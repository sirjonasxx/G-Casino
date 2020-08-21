package controller;

import gearth.extensions.Extension;
import gearth.extensions.ExtensionInfo;
import gearth.extensions.extra.harble.HashSupport;
import gearth.protocol.HMessage;
import misc.Constants;
import virtualroom.VirtualRoom;
import virtualroom.VirtualRoomMaintainer;
import virtualroom.config.generator.ConfigTextTest;

import java.util.HashMap;
import java.util.Map;

@ExtensionInfo(
        Title =  "G-Casino",
        Description =  "Bring back the 2011 vibes",
        Version =  "0.1",
        Author =  "sirjonasxx"
)
public class GCasino extends Extension {

    public static void main(String[] args) {
        new GCasino(args).run();
    }

    public static Map<Extension, HashSupport> hashSupportForExtension = new HashMap<>();

    private HashSupport hashSupport;
    private VirtualRoomMaintainer provider = null;
    private VirtualRoom virtualRoom = null;

    private int roomId = -1;

    public GCasino(String[] args) {
        super(args);
    }

    @Override
    protected void initExtension() {
        hashSupport = new HashSupport(this);
        hashSupportForExtension.put(this, hashSupport);

        hashSupport.intercept(HMessage.Direction.TOSERVER, "RequestRoomHeightmap", this::onRoomEnter);

        hashSupport.intercept(HMessage.Direction.TOCLIENT, "RoomHeightMap", hMessage -> {
            if (provider != null) provider.onHeightMapSet(hMessage);
        });
        hashSupport.intercept(HMessage.Direction.TOCLIENT, "RoomFloorItems", hMessage -> {
            if (provider != null) provider.onFloorFurniLoad(hMessage);
        });
        hashSupport.intercept(HMessage.Direction.TOCLIENT, "RoomWallItems", hMessage -> {
            if (provider != null) provider.onWallFurniLoad(hMessage);
        });
        hashSupport.intercept(HMessage.Direction.TOCLIENT, "RoomUsers", hMessage -> {
            if (provider != null) provider.onRoomUsersLoad(hMessage);
        });
        hashSupport.intercept(HMessage.Direction.TOCLIENT, "RoomUserRemove", hMessage -> {
            if (provider != null) provider.onRoomUserLeave(hMessage);
        });
        hashSupport.intercept(HMessage.Direction.TOCLIENT, "RoomUserStatus", hMessage -> {
            if (provider != null) provider.onRoomUserStatusUpdate(hMessage);
        });
        hashSupport.intercept(HMessage.Direction.TOCLIENT, "RoomThickness", hMessage -> {
            if (provider != null) provider.onRoomTicknessSet(hMessage);
        });
        intercept(HMessage.Direction.TOCLIENT, hMessage -> {
            if (provider != null) provider.maybeBufferPacket(hMessage);
        });

        hashSupport.intercept(HMessage.Direction.TOCLIENT, "RoomUserData", hMessage -> {  // RoomUserData?
            if (provider != null) provider.onRoomUserDataUpdate(hMessage);
        });
        hashSupport.intercept(HMessage.Direction.TOCLIENT, "RoomModel", hMessage -> {  // room model and id
            hMessage.getPacket().readString();
            roomId = (hMessage.getPacket().readInteger());
        });


        hashSupport.intercept(HMessage.Direction.TOCLIENT, "ItemExtraData", hMessage -> {
            if (virtualRoom != null) virtualRoom.onItemDataUpdate(hMessage);
        });
        hashSupport.intercept(HMessage.Direction.TOSERVER, "CloseDice", hMessage -> {
            if (virtualRoom != null) virtualRoom.onDiceClickOut(hMessage);
        });
        hashSupport.intercept(HMessage.Direction.TOSERVER, "TriggerDice", hMessage -> {
            if (virtualRoom != null) virtualRoom.onDiceClickOut(hMessage);
        });



        if (Constants.DEVELOPMODE) {
            hashSupport.intercept(HMessage.Direction.TOSERVER, "RoomUserTalk", hMessage -> {
                String speech = hMessage.getPacket().readString();
                if (speech.equals("buygift")) {
                    hMessage.setBlocked(true);
                    String config = ConfigTextTest.generate();
                    System.out.println(config);

                    hashSupport.sendToServer("CatalogBuyItemAsGift",
                            889383,
                            5046,
                            config,
                            "illvetersc60",
                            "",
                            190, 0, 0, false);
                }
            });
        }

    }


    private void onRoomEnter(HMessage hMessage) {
        virtualRoom = null;
        provider = new VirtualRoomMaintainer(this, virtualRoom -> {
            this.virtualRoom = virtualRoom;
            if (virtualRoom == null) {
                provider = null;
            }
        }, roomId);
    }
}
