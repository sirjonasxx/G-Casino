package virtualroom;

import gearth.extensions.Extension;
import gearth.extensions.parsers.HEntity;
import gearth.extensions.parsers.HEntityUpdate;
import gearth.extensions.parsers.HFloorItem;
import gearth.protocol.HMessage;
import gearth.protocol.HPacket;

import java.util.LinkedList;

public class VirtualRoomMaintainer {

    private Extension extension;
    private OnVirtualRoomLoaded callback;
    private VirtualRoomConfig virtualRoomConfig = null;
    private RoomUsers roomUsers = new RoomUsers();
    private volatile int roomId = -1;

    private HMessage bufferHeightMap = null;
    private LinkedList<HMessage> buffered = new LinkedList<>();

    public VirtualRoomMaintainer(Extension extension, OnVirtualRoomLoaded onVirtualRoomLoaded, int roomId) {
        this.extension = extension;
        this.callback = onVirtualRoomLoaded;
        this.roomId = roomId;
    }


    public void onFloorFurniLoad(HMessage hMessage) {
        HFloorItem[] hFloorItems = HFloorItem.parse(hMessage.getPacket());
        virtualRoomConfig = new VirtualRoomConfig(hFloorItems, roomId, extension, bufferHeightMap);

        if (buffered.getLast().getIndex() == hMessage.getIndex()) {
            buffered.removeLast();
            hMessage.setBlocked(false);
        }
        buffered.forEach(hMessage1 -> {
            extension.sendToClient(hMessage1.getPacket());
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        if (virtualRoomConfig.isVirtualRoom() && roomId != -1) {
            callback.virtualRoomLoaded(new VirtualRoom(extension, virtualRoomConfig, roomUsers));

            hMessage.getPacket().resetReadIndex();
            virtualRoomConfig.replaceFloorFurniture(hMessage);
        }
        else {
            callback.virtualRoomLoaded(null);
        }
    }
    public void onWallFurniLoad(HMessage hMessage) {
        if (virtualRoomConfig != null && virtualRoomConfig.isVirtualRoom()) {
            virtualRoomConfig.replaceWallFurniture(hMessage);
        }
    }
    public void onRoomTicknessSet(HMessage hMessage) {
        if (virtualRoomConfig != null && virtualRoomConfig.isVirtualRoom()) {
            virtualRoomConfig.replaceRoomTickness(hMessage);
        }
    }
    public void onHeightMapSet(HMessage hMessage) {
        bufferHeightMap = hMessage;
        hMessage.setBlocked(true);
    }

    public void maybeBufferPacket(HMessage hMessage) {
        if (bufferHeightMap != null && hMessage.getIndex() > bufferHeightMap.getIndex() && virtualRoomConfig == null) {
            buffered.add(hMessage);
            hMessage.setBlocked(true);
        }
    }

    public void onRoomUsersLoad(HMessage hMessage) {
        HEntity[] users = HEntity.parse(hMessage.getPacket());
        for (HEntity user : users) {
            roomUsers.userEnter(user);
        }
    }
    public void onRoomUserLeave(HMessage hMessage) {
        roomUsers.userLeave(Integer.parseInt(hMessage.getPacket().readString()));
    }
    public void onRoomUserStatusUpdate(HMessage hMessage) {
        HEntityUpdate[] updates = HEntityUpdate.parse(hMessage.getPacket());

        for (HEntityUpdate update : updates) {
            int userIndex = update.getIndex();
            HEntity entity = roomUsers.getUserForIndex(userIndex);
            if (entity != null) {
                entity.tryUpdate(update);
                roomUsers.userMove(entity.getIndex(), entity.getTile());
            }
        }
    }
    public void onRoomUserDataUpdate(HMessage hMessage) {
        HPacket packet = hMessage.getPacket();
        int index = packet.readInteger();
        String figureId = packet.readString();
        String gender = packet.readString();
        String motto = packet.readString();

        if (index != -1) {
            roomUsers.userMottoMaybeUpdate(index, motto);
        }
    }

}
