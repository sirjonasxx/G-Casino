package virtualroom.config;

import fakecharsets.FakeCharSet;
import misc.Constants;
import misc.TextReader;

class VRUtils {

    private static FakeCharSet decoder = Constants.giftFakeCharset;

    static String readString(TextReader reader, int length) {
        return reader.read(length);
    }

    static String readVarString(TextReader reader, int lengthLength) {
        return reader.read(
                (int) decoder.decodeString(reader.read(lengthLength)));
    }

    static int readInteger(TextReader reader, int length, boolean maybeNegative) {
        boolean val_isNeg = false;
        if (maybeNegative) {
            val_isNeg = decoder.decodeString(reader.read(1)) != 0;
        }

        int val_temp = (int)decoder.decodeString(reader.read(length));
        return val_isNeg ? -val_temp : val_temp;
    }

    static double readDouble(TextReader reader, boolean maybeNegative) {
        boolean val_isNeg = false;
        if (maybeNegative) {
            val_isNeg = decoder.decodeString(reader.read(1)) != 0;
        }

        int val_1 = (int)decoder.decodeString(reader.read(1));
        int val_2 = (int)decoder.decodeString(reader.read(1));
        double val_temp = (double)val_1 + (((double)val_2) * (1.0/decoder.getRange()));
        return val_isNeg ? -val_temp : val_temp;
    }

    static boolean readBoolean(TextReader reader) {
        return decoder.decodeString(reader.read(1)) != 0;
    }

}
