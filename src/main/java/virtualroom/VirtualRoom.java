package virtualroom;

import controller.GCasino;
import gearth.extensions.Extension;
import gearth.extensions.extra.harble.HashSupport;
import gearth.protocol.HMessage;
import gearth.protocol.HPacket;
import virtualroom.config.VRSeed;

import java.util.*;

public class VirtualRoom {

    private final Extension extension;
    private final VirtualRoomConfig config;
    private final RoomUsers roomUsers;
    private final HashSupport hashSupport;

    private Map<Integer, List<Integer>> idToSeedings = new HashMap<>();
    private final Set<Integer> delayedRoll = new HashSet<>();
    private Map<Integer, Long> latestRoll = new HashMap<>();

    VirtualRoom(Extension extension, VirtualRoomConfig config, RoomUsers roomUsers) {
        this.extension = extension;
        this.config = config;
        this.roomUsers = roomUsers;
        hashSupport = GCasino.hashSupportForExtension.get(extension);
    }

    public void onItemDataUpdate(HMessage hMessage) {
        try {
            HPacket packet = hMessage.getPacket();
            int furniId = Integer.parseInt(packet.readString());
            packet.readInteger();
            String state = packet.readString();

            if (config.getDices().contains(furniId)) {
                hMessage.setBlocked(true);
                idToSeedings.remove(furniId);

                if (state.equals("0")) {
                    // dice is closed
                    synchronized (delayedRoll) {
                        delayedRoll.remove(furniId);
                    }

                    hashSupport.sendToClient("ItemStateComposer2",
                            furniId,
                            0
                    );
                }
                else {
                    // roll dice

                    idToSeedings.put(furniId, new ArrayList<>());
                    latestRoll.put(furniId, System.currentTimeMillis());
                    hashSupport.sendToClient("ItemStateComposer2",
                            furniId,
                            -1
                    );
                }
            }

            VRSeed seed = config.getSeed();
            if (seed != null && seed.getFurniId() == furniId) {

                List<Integer> finishedDices = new ArrayList<>();
                for (int diceId : idToSeedings.keySet()) {
                    idToSeedings.get(diceId).add(Integer.parseInt(state));
                    if (idToSeedings.get(diceId).size() >= seed.getAmount()) {
                        finishedDices.add(diceId);
                    }
                }

                finishedDices.sort(Comparator.comparingLong(o -> latestRoll.get(o)));
                for (int i = 0; i < finishedDices.size(); i++) {
                    int diceId = finishedDices.get(i);
                    int result = generateNumberForSeedings(diceId, idToSeedings.remove(diceId));

                    int[] delay = {200 * i};
                    synchronized (delayedRoll) {
                        delayedRoll.add(diceId);
                    }

                    new Thread(() -> {
                        try {
                            Thread.sleep(delay[0]);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        synchronized (delayedRoll) {
                            delayedRoll.remove(diceId);
                            hashSupport.sendToClient("ItemStateComposer2",
                                    diceId,
                                    result
                            );
                        }

                    }).start();

                }
            }
        }
        catch (Exception ignore) {}
    }

    private int generateNumberForSeedings(int furniId, List<Integer> seedings) {
        int seed = 0;
        for (int c = 0; c < seedings.size(); c++) {
            seed *= config.getSeed().getStates();
            seed += seedings.get(c);
        }

        Random result = new Random(seed + furniId);

        return result.nextInt(6) + 1;
    }


    public void onDiceClickOut(HMessage hMessage) {
        HPacket packet = hMessage.getPacket();
        int maybeFakeDiceId = packet.readInteger();

        if (config.getDices().contains(maybeFakeDiceId)) {
            hMessage.setBlocked(true);

            hashSupport.sendToServer("ToggleFloorItem",
                    maybeFakeDiceId,
                    0
            );
        }
    }

}
