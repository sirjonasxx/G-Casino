package virtualroom.config.generator;

import fakecharsets.FakeCharSet;

public class FloorItemMap {

    private final int furniId;
    private final int newTypeId;
    private final double z;

    public FloorItemMap(int furniId, int newTypeId, double z) {
        this.furniId = furniId;
        this.newTypeId = newTypeId;
        this.z = z;
    }

    public String encode(FakeCharSet encoder) {
        StringBuilder builder = new StringBuilder();
        builder.append(encoder.encodeInt(furniId, 6) +
                encoder.encodeInt(newTypeId, 2));

        builder.append(encoder.encodeInt(z < 0 ? 1 : 0, 1));
        int z1 = (int) Math.floor(Math.abs(z));
        double smallest = 1.0 /encoder.getRange();
        int z2 = (int) Math.floor((Math.abs(z) - ((double) z1)) / smallest);

        builder.append(encoder.encodeInt(z1, 1));
        builder.append(encoder.encodeInt(z2, 1));

        return builder.toString();
    }

    public int getFurniId() {
        return furniId;
    }

    public int getNewTypeId() {
        return newTypeId;
    }

    public double getZ() {
        return z;
    }
}
