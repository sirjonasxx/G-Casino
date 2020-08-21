package virtualroom.config.generator;

import fakecharsets.FakeCharSet;
import gearth.extensions.parsers.HPoint;
import misc.Constants;

import java.util.ArrayList;
import java.util.List;

public class ConfigTextGenerator {

    private StringBuilder builder;
    private FakeCharSet encoder;

    public ConfigTextGenerator() {
        builder = new StringBuilder();
        encoder = Constants.giftFakeCharset;
        init();
    }

    private void init() {
        builder.append(Constants.key + "\n");
    }

        private void announceNext(String n) {
        builder.append("\n").append(n).append(".");
    }

    private void appendString(int announceLength, String s) {
        builder.append(encoder.encodeInt(s.length(), announceLength))
                .append(s);
    }

    public void appendVersion() {
        announceNext("1");
        appendString(1, Constants.version);
    }

    public void appendStayFurniture(List<Integer> stayFurniture) {
        announceNext("2");

        builder.append(encoder.encodeInt(stayFurniture.size(), 2));
        for (Integer stayFurni : stayFurniture) {
            builder.append(encoder.encodeInt(stayFurni, 6));
        }
    }

    public void appendMapping(List<FloorItemMap> floorItemMapping) {
        announceNext("3");

        builder.append(encoder.encodeInt(floorItemMapping.size(), 2));
        for (FloorItemMap floorItemMap : floorItemMapping) {
            builder.append(floorItemMap.encode(encoder));
        }
    }

    public void appendFloorFurniture(List<FakeFloorItem> fakeFloorItems) {
        announceNext("4");

        builder.append(encoder.encodeInt(fakeFloorItems.size(), 2));
        for (FakeFloorItem fakeFloorItem : fakeFloorItems) {
            builder.append(fakeFloorItem.encode(encoder));
        }
    }

    public void appendWallFurniture(List<FakeWallItem> fakeWallItems) {
        announceNext("5");

        builder.append(encoder.encodeInt(fakeWallItems.size(), 2));
        for (FakeWallItem fakeWallItem : fakeWallItems) {
            builder.append(fakeWallItem.encode(encoder));
        }
    }

    public void appendThickness(boolean invisibleWall, int wallThickness, int floorThickness) {
        announceNext("7");

        builder.append(encoder.encodeInt(invisibleWall ? 1 : 0, 1));

        builder.append(encoder.encodeInt(wallThickness < 0 ? 1 : 0, 1));
        builder.append(encoder.encodeInt(Math.abs(wallThickness), 1));

        builder.append(encoder.encodeInt(floorThickness < 0 ? 1 : 0, 1));
        builder.append(encoder.encodeInt(Math.abs(floorThickness), 1));
    }

    public void appendHeightmap(String heightMap) {
        announceNext("8");
        appendString(2, heightMap);
    }

    public void appendInitDoubleClickFurni(int id) {
        announceNext("9");
        builder.append(encoder.encodeInt(id, 6));
    }

    public void appendWelcomeMessage(String message) {
        announceNext("a");
        appendString(2, message);
    }

    public void appendStayWallFurniture(List<Integer> stayWallFurniture) {
        announceNext("b");

        builder.append(encoder.encodeInt(stayWallFurniture.size(), 2));
        for (Integer stayWallFurni : stayWallFurniture) {
            builder.append(encoder.encodeInt(stayWallFurni, 6));
        }
    }

    // false by default
    public void appendMottoChat(boolean enable) {
        announceNext("c");
        builder.append(encoder.encodeInt(enable ? 1 : 0, 1));
    }

    public void appendRoomPaint(String wallpaper, String floor, String landscape) {
        announceNext("d");
        appendString(1, wallpaper);
        appendString(1, floor);
        appendString(1, landscape);
    }

    public void appendSeed(int id, int states, int amount) {
        announceNext("e");
        builder.append(encoder.encodeInt(id, 6));
        builder.append(encoder.encodeInt(states, 1));
        builder.append(encoder.encodeInt(amount, 1));
    }

    // false by default
    public void appendShoutSuppress(boolean enable) {
        announceNext("f");
        builder.append(encoder.encodeInt(enable ? 1 : 0, 1));
    }


    @Override
    public String toString() {
        return builder.toString();
    }


}
