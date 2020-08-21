package virtualroom.config;

import misc.Constants;
import misc.TextReader;

public class VRInitDoubleClickFurni {

    private int furniId;

    private VRInitDoubleClickFurni(int furniId) {
        this.furniId = furniId;
    }

    public int getFurniId() {
        return furniId;
    }


    public static VRInitDoubleClickFurni parse(TextReader reader) {
        int furniId = (int) Constants.giftFakeCharset.decodeString(reader.read(6));
        return new VRInitDoubleClickFurni(furniId);
    }
}
