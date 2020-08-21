package virtualroom.config;

import fakecharsets.FakeCharSet;
import misc.Constants;
import misc.TextReader;

public class VRThickness {

    private final boolean invisibleWall;
    private final int wallThickness;
    private final int floorThickness;

    private VRThickness(boolean invisibleWall, int wallThickness, int floorThickness) {
        this.invisibleWall = invisibleWall;
        this.wallThickness = wallThickness;
        this.floorThickness = floorThickness;
    }

    public boolean isInvisibleWall() {
        return invisibleWall;
    }

    public int getWallThickness() {
        return wallThickness;
    }

    public int getFloorThickness() {
        return floorThickness;
    }


    public static VRThickness parse(TextReader reader) {
        FakeCharSet decoder = Constants.giftFakeCharset;

        boolean invisibleWall = VRUtils.readBoolean(reader);

        int wallThickness = VRUtils.readInteger(reader, 1, true);
        int floorThickness = VRUtils.readInteger(reader, 1, true);

        return new VRThickness(invisibleWall, wallThickness, floorThickness);
    }
}
