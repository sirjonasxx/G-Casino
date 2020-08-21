package virtualroom.config.generator;

import fakecharsets.FakeCharSet;
import gearth.extensions.parsers.HPoint;

public class FakeFloorItem {

    private final int typeId;
    private final HPoint hPoint;
    private final int rotation;
    private final String state;

    public FakeFloorItem(int typeId, HPoint hPoint, int rotation, String state) {
        this.typeId = typeId;
        this.hPoint = hPoint;
        this.rotation = rotation;
        this.state = state;
    }

    public String encode(FakeCharSet encoder) {
        StringBuilder builder = new StringBuilder();

        builder.append(encoder.encodeInt(typeId, 2));
        builder.append(encoder.encodeInt(hPoint.getX(), 1));
        builder.append(encoder.encodeInt(hPoint.getY(), 1));

        double z = hPoint.getZ();
        builder.append(encoder.encodeInt(z < 0 ? 1 : 0, 1));
        z = Math.abs(z);
        int z1 = (int) Math.floor(z);
        double smallest = 1.0 /encoder.getRange();
        int z2 = (int) Math.floor((z - ((double) z1)) / smallest);

        builder.append(encoder.encodeInt(z1, 1));
        builder.append(encoder.encodeInt(z2, 1));

        builder.append(encoder.encodeInt(rotation, 1));

        builder.append(encoder.encodeInt(state.length(), 2));
        builder.append(state);

        return builder.toString();
    }


    public int getTypeId() {
        return typeId;
    }

    public HPoint gethPoint() {
        return hPoint;
    }

    public int getRotation() {
        return rotation;
    }

    public String getState() {
        return state;
    }
}
