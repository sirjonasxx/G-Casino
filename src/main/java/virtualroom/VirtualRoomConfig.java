package virtualroom;

import controller.GCasino;
import gearth.extensions.Extension;
import gearth.extensions.parsers.HFloorItem;
import gearth.extensions.parsers.HPoint;
import gearth.extensions.parsers.HWallItem;
import gearth.protocol.HMessage;
import gearth.protocol.HPacket;
import misc.Constants;
import virtualroom.config.*;
import virtualroom.config.generator.ConfigTextTest;

import java.util.*;

public class VirtualRoomConfig {

    // room is configured by gifts, which can have a bobba-proof length of 3900

    private boolean isVirtualRoom;

    private Set<Integer> stayFurniture;
    private Set<Integer> stayWallFurniture;
    private Map<Integer, Integer> mapFurniIDToTypeId;
    private Map<Integer, Double> mapFurniIDToHeight;
    private List<HFloorItem> addedFloorItems;
    private List<HWallItem> addedWallItems;
    private Set<Integer> dices;

    private HFloorItem[] originalFloorItems;
    private final int roomId;

    private VRThickness thickness = null;
    private VRHeightMap heightMap = null;
    private VRSeed seed = null;
    private volatile boolean useMottoChat = false;
    private volatile boolean suppressShout = false;

    private final Extension extension;

    // initialized with the RoomFloorItems packet
    public VirtualRoomConfig(HFloorItem[] floorItems, int roomId, Extension extension, HMessage heightMapBuffer) {
        this.roomId = roomId;
        this.extension = extension;

        if (Constants.DEVELOPMODE) {
            if (roomId == 39104596) {
                parse(new String[]{ConfigTextTest.generate()}, floorItems);
            }
            else {
                parse(new String[]{}, floorItems);
            }
        }
        else {
            List<String> settingGifts = new ArrayList<>();
            for (HFloorItem floorItem : floorItems) {
                if (floorItem.getCategory() == 1) {
                    Object[] stuff = floorItem.getStuff();
                    int length = (int)floorItem.getStuff()[0];
                    for (int i = 0; i < length; i++) {
                        if (stuff[i + 1].equals("EXTRA_PARAM")) {
                            settingGifts.add((String)stuff[i + 2]);
                        }
                    }
                }
            }

            parse(settingGifts.toArray(new String[0]), floorItems);
        }

        if (isVirtualRoom && heightMap != null) {
            heightMapBuffer.getPacket().replaceString(11, heightMap.getHeightMap());
        }
        extension.sendToClient(heightMapBuffer.getPacket());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public boolean isVirtualRoom() {
        return isVirtualRoom;
    }

    public Set<Integer> getDices() {
        return dices;
    }

    void replaceFloorFurniture(HMessage hMessage) {
        if (isVirtualRoom()) {
            hMessage.setBlocked(true);
            HFloorItem[] floorItems = HFloorItem.parse(hMessage.getPacket());

            List<HFloorItem> keep = new ArrayList<>();

            for (HFloorItem floorItem : floorItems) {
                if (mapFurniIDToTypeId.containsKey(floorItem.getId())) {
                    floorItem.setTypeId(mapFurniIDToTypeId.get(floorItem.getId()));
                    floorItem.setTile(new HPoint(
                            floorItem.getTile().getX(),
                            floorItem.getTile().getY(),
                            mapFurniIDToHeight.get(floorItem.getId())
                    ));
                    keep.add(floorItem);

                    if(dices.contains(floorItem.getId())) {
                        if (floorItem.getCategory() == 0) {
                            String state = (String)floorItem.getStuff()[0];
                            if (!state.equals("0")) {
                                floorItem.setStuff(new Object[]{"-1"});
                            }
                        }
                    }
                }
                else if(stayFurniture.contains(floorItem.getId())) {
                    keep.add(floorItem);
                }
            }

            keep.addAll(addedFloorItems);
            HPacket newFloorItemsPacket = HFloorItem.constructPacket(
                    keep.toArray(new HFloorItem[0]),
                    hMessage.getPacket().headerId()
            );

            extension.sendToClient(newFloorItemsPacket);
        }
    }
    void replaceWallFurniture(HMessage hMessage) {
        if (isVirtualRoom()) {
            hMessage.setBlocked(true);

            HWallItem[] hWallItems = HWallItem.parse(hMessage.getPacket());
            List<HWallItem> keep = new ArrayList<>();
            for (HWallItem hWallItem : hWallItems) {
                if (stayWallFurniture.contains(hWallItem.getId())) {
                    keep.add(hWallItem);
                }
            }

            keep.addAll(addedWallItems);

            HPacket packet = HWallItem.constructPacket(
                    keep.toArray(new HWallItem[0]),
                    hMessage.getPacket().headerId()
            );

            extension.sendToClient(packet);
        }
    }
    void replaceRoomTickness(HMessage hMessage) {
        if (thickness != null) {
            HPacket packet = hMessage.getPacket();
            packet.replaceBoolean(6, thickness.isInvisibleWall());
            packet.replaceInt(7, thickness.getWallThickness());
            packet.replaceInt(11, thickness.getFloorThickness());
        }
    }


    private void parse(String[] settings, HFloorItem[] floorItems) {
        Map<Integer, HFloorItem> idToItem = new HashMap<>();
        for (HFloorItem floorItem : floorItems) {
            idToItem.put(floorItem.getId(), floorItem);
        }

        originalFloorItems = floorItems;
        addedWallItems = new ArrayList<>();
        stayFurniture = new HashSet<>();
        stayWallFurniture = new HashSet<>();
        mapFurniIDToTypeId = new HashMap<>();
        mapFurniIDToHeight = new HashMap<>();
        addedFloorItems = new ArrayList<>();
        dices = new HashSet<>();

        try {
            isVirtualRoom = false;

            for (String setting : settings) {
                List<Object> objects = VRParser.parse(setting);

                if (objects == null) {
                    continue;
                }

                for (Object object : objects) {
                    if (object instanceof VRVersion) {
                        VRVersion vrVersion = (VRVersion)object;
                        if (vrVersion.isValid()) {
                            isVirtualRoom = true;
                            System.out.println("found valid room");
                        }
                    }
                    else if (object instanceof VRStayFurni) {
                        VRStayFurni vrStayFurni = (VRStayFurni)object;
                        stayFurniture.addAll(vrStayFurni.getStayFurniIds());
                    }
                    else if (object instanceof VRMapFurni) {
                        VRMapFurni vrMapFurni = (VRMapFurni)object;
                        mapFurniIDToTypeId.putAll(vrMapFurni.getFurniIdToTypeId());
                        mapFurniIDToHeight.putAll(vrMapFurni.getFurniIdToHeight());

                        for (Integer id : vrMapFurni.getFurniIdToTypeId().keySet()) {
                            int typeIdMapping = vrMapFurni.getFurniIdToTypeId().get(id);
                            if (Constants.dicesTypeIdPerHotel.get(Constants.HOTEL).contains(typeIdMapping) &&
                                    Constants.floorSwitchPerHotel.get(Constants.HOTEL) == idToItem.get(id).getTypeId()) {
                                dices.add(id);
                            }
                        }
                    }
                    else if (object instanceof VRAddFurni) {
                        VRAddFurni vrAddFurni = (VRAddFurni)object;
                        addedFloorItems.addAll(vrAddFurni.gethFloorItems());
                    }
                    else if (object instanceof VRWallFurni) {
                        VRWallFurni vrWallFurni = (VRWallFurni)object;
                        addedWallItems.addAll(vrWallFurni.gethWallItems());
                    }
                    else if (object instanceof VRThickness) {
                        this.thickness = (VRThickness)object;
                    }
                    else if (object instanceof VRHeightMap) {
                        this.heightMap = (VRHeightMap)object;
                    }
                    else if (object instanceof VRInitDoubleClickFurni) {
                        VRInitDoubleClickFurni vrInitDoubleClickFurni = (VRInitDoubleClickFurni)object;
                        int furniId = vrInitDoubleClickFurni.getFurniId();

                        GCasino.hashSupportForExtension.get(extension)
                                .sendToServer("ToggleFloorItem", furniId, 0);
                    }
                    else if (object instanceof VRWelcomeMessage) {
                        VRWelcomeMessage vrWelcomeMessage = (VRWelcomeMessage)object;
                        String message = vrWelcomeMessage.getWelcomeMessage();

                        // {l}{u:374}{i:index}{s:"hoi"}{i:0}{i:color}{i:0}{i:1}
                        new Thread(() -> {
                            try {
                                Thread.sleep(800);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            GCasino.hashSupportForExtension.get(extension)
                                    .sendToClient("RoomUserWhisper",
                                            -1,
                                            message,
                                            0, 33, 0, 0
                                    );
                        }).start();
                    }
                    else if (object instanceof VRStayWallFurni) {
                        VRStayWallFurni vrStayWallFurni = (VRStayWallFurni)object;
                        stayWallFurniture.addAll(vrStayWallFurni.getStayWallFurni());
                    }
                    else if (object instanceof VRMottoChat) {
                        VRMottoChat vrMottoChat = (VRMottoChat)object;
                        useMottoChat = vrMottoChat.useMottoChat();
                    }
                    else if (object instanceof VRRoomPaint) {
                        VRRoomPaint roomPaint = (VRRoomPaint)object;
                        String wallpaper = roomPaint.getWallpaper();
                        String floor = roomPaint.getFloor();
                        String landscape = roomPaint.getLandscape();

                        if (wallpaper != null) {
                            GCasino.hashSupportForExtension.get(extension)
                                    .sendToClient("RoomPaint", "wallpaper", wallpaper);
                        }
                        if (floor != null) {
                            GCasino.hashSupportForExtension.get(extension)
                                    .sendToClient("RoomPaint", "floor", floor);
                        }
                        if (landscape != null) {
                            GCasino.hashSupportForExtension.get(extension)
                                    .sendToClient("RoomPaint", "landscape", landscape);
                        }
                    }
                    else if (object instanceof VRSeed) {
                        this.seed = (VRSeed)object;
                    }
                    else if (object instanceof VRSuppressShout) {
                        VRSuppressShout suppressShout = (VRSuppressShout)object;
                        this.suppressShout = suppressShout.isSuppressShout();
                    }
                }
            }
        }
        catch (Exception e) {
            isVirtualRoom = false;
        }


    }

    public VRSeed getSeed() {
        return seed;
    }

    public boolean isSuppressShout() {
        return suppressShout;
    }
}
