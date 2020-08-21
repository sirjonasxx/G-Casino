package virtualroom.config.generator;

import fakecharsets.FakeCharSet;
import gearth.extensions.parsers.HPoint;

public class FakeWallItem {

    private final int typeId;
    private final HPoint hPoint;
    private final int l1;
    private final int l2;
    private final String lr;
    private final String state;

    public FakeWallItem(int typeId, HPoint hPoint, int l1, int l2, String lr, String state) {
        this.typeId = typeId;
        this.hPoint = hPoint;
        this.l1 = l1;
        this.l2 = l2;
        this.lr = lr;
        this.state = state;
    }

    public String encode(FakeCharSet encoder) {
        StringBuilder builder = new StringBuilder();
        builder.append(encoder.encodeInt(typeId, 2));
        builder.append(encoder.encodeInt(hPoint.getX(), 1));
        builder.append(encoder.encodeInt(hPoint.getY(), 1));

        builder.append(encoder.encodeInt(l1 < 0 ? 1 : 0, 1));
        builder.append(encoder.encodeInt(Math.abs(l1), 1));

        builder.append(encoder.encodeInt(l2 < 0 ? 1 : 0, 1));
        builder.append(encoder.encodeInt(Math.abs(l2), 2));

        builder.append(lr);
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

    public int getL1() {
        return l1;
    }

    public int getL2() {
        return l2;
    }

    public String getLr() {
        return lr;
    }
}
