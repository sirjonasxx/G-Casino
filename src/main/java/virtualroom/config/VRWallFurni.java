package virtualroom.config;

import gearth.extensions.parsers.HWallItem;
import gearth.protocol.HPacket;
import misc.TextReader;

import java.util.ArrayList;
import java.util.List;

public class VRWallFurni {

    private List<HWallItem> hWallItems;

    private VRWallFurni(List<HWallItem> hWallItems) {
        this.hWallItems = hWallItems;
    }

    public List<HWallItem> gethWallItems() {
        return hWallItems;
    }


    private static int furniIdCount = 1010000000;

    public static VRWallFurni parse(TextReader reader) {
        List<HWallItem> hWallItems = new ArrayList<>();

        int amount = VRUtils.readInteger(reader, 2, false);
        for (int i = 0; i < amount; i++) {
            int typeId = VRUtils.readInteger(reader, 2, false);

            int x = VRUtils.readInteger(reader, 1, false);
            int y = VRUtils.readInteger(reader, 1, false);

            int l1 = VRUtils.readInteger(reader, 1, true);
            int l2 = VRUtils.readInteger(reader, 2, true);
            String lr = VRUtils.readString(reader, 1);

            String state = VRUtils.readVarString(reader, 2);

            // -------------------------------------------------------

            HPacket fakeHWallItem = new HPacket(
                    9999,
                    (furniIdCount++) + "",
                    typeId,
                    String.format(":w=%d,%d l=%d,%d %s",
                            x, y, l1, l2, lr),
                    state,
                    -1,
                    0,
                    1337
            );

            HWallItem hWallItem = new HWallItem(fakeHWallItem);
            hWallItem.setOwnerName("G-Earth");

            hWallItems.add(hWallItem);
        }


        return new VRWallFurni(hWallItems);
    }

}
