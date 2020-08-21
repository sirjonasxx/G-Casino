package virtualroom.config;

import fakecharsets.FakeCharSet;
import gearth.extensions.parsers.HFloorItem;
import gearth.protocol.HPacket;
import misc.Constants;
import misc.TextReader;

import java.util.ArrayList;
import java.util.List;

public class VRAddFurni {

    private List<HFloorItem> hFloorItems;

    private VRAddFurni(List<HFloorItem> hFloorItems) {
        this.hFloorItems = hFloorItems;
    }

    public List<HFloorItem> gethFloorItems() {
        return hFloorItems;
    }


    private static int furniIdCount = 1000000000;
    public static VRAddFurni parse(TextReader reader) {
        List<HFloorItem> hFloorItems = new ArrayList<>();

        int amount = VRUtils.readInteger(reader, 2, false);
        for (int i = 0; i < amount; i++) {
            int typeId = VRUtils.readInteger(reader, 2, false);

            int x = VRUtils.readInteger(reader, 1, false);
            int y = VRUtils.readInteger(reader, 1, false);

            double z = VRUtils.readDouble(reader, true);

            int rotation = VRUtils.readInteger(reader, 1, false);

            String state = VRUtils.readVarString(reader, 2);

            // ------------------------------------------------------------



            HPacket fakeHFloorItem = new HPacket(
                    9999,
                    furniIdCount++,
                    typeId,
                    x,
                    y,
                    rotation,
                    z + "",
                    "0.0",
                    0,
                    0, // category
                    state,
                    -1, // rental timeout
                    0,
                    1337
                    );

            HFloorItem hFloorItem = new HFloorItem(fakeHFloorItem);
            hFloorItem.setOwnerName("G-Earth");

            hFloorItems.add(hFloorItem);

        }

        return new VRAddFurni(hFloorItems);
    }
}
