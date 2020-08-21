package virtualroom.config;

import fakecharsets.FakeCharSet;
import misc.Constants;
import misc.TextReader;

public class VRRoomPaint {

    private final String wallpaper;
    private final String floor;
    private final String landscape;


    private VRRoomPaint(String wallpaper, String floor, String landscape) {
        this.wallpaper = wallpaper;
        this.floor = floor;
        this.landscape = landscape;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public String getFloor() {
        return floor;
    }

    public String getLandscape() {
        return landscape;
    }


    public static VRRoomPaint parse(TextReader reader) {
        String wallpaper = VRUtils.readVarString(reader, 1);
        String floor = VRUtils.readVarString(reader, 1);
        String landscape = VRUtils.readVarString(reader, 1);

        return new VRRoomPaint(
                wallpaper.equals("") ? null : wallpaper,
                floor.equals("") ? null : floor,
                landscape.equals("") ? null : landscape
        );
    }
}
