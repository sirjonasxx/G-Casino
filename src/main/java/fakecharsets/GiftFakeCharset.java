package fakecharsets;

import gearth.protocol.HPacket;

import java.util.HashMap;
import java.util.Map;

public class GiftFakeCharset implements FakeCharSet {

    private String intToChar;
    private Map<Character, Integer> charToInt;

    public GiftFakeCharset() {
//        intToChar =
////                "!\"#$%&'()*+,-./" +
////                        "0123456789:;<=>?" +
////                        "@ABCDEFGHIJKLMNO" +
////                        "PQRSTUVWXYZ[\\]^_" +
////                        "`abcdefghijklmno" +
////                        "pqrstuvwxyz{|}~" +
////                        "" +
////                        "¡¢£¤¥¦§¨©ª«¬®¯" +
////                        "°±²³´µ¶·¸¹º»¼½¾¿" +
////                        "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏ" +
////                        "ÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞß" +
////                        "àáâãäåæçèéêëìíîï" +
////                        "ðñòóôõö÷øùúûüýþÿ";

        intToChar =
                "!\"#$%&'()*+,-./" +
                        "0123456789:;<=>?" +
                        "@ABCDEFGHIJKLMNO" +
                        "PQRSTUVWXYZ[\\]^_" +
                        "`abcdefghijklmno" +
                        "pqrstuvwxyz{|}~";

        charToInt = new HashMap<>();
        for (int i = 0; i < intToChar.length(); i++) {
            charToInt.put(intToChar.charAt(i), i);
        }
    }

    @Override
    public int getRange() {
        return intToChar.length();
    }

    @Override
    public String encodeInt(long number, int length) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            long next = number % getRange();
            number = number / getRange();

            stringBuilder.append(intToChar.charAt((int)next));
        }

        return stringBuilder.reverse().toString();
    }

    @Override
    public long decodeString(String compressed) {
        int result = 0;

        for (int i = 0; i < compressed.length(); i++) {
            result *= getRange();
            char c = compressed.charAt(i);
            int decoded = charToInt.get(c);
            result += decoded;
        }

        return result;
    }


    public static void main(String[] args) {
        GiftFakeCharset fakeCharset = new GiftFakeCharset();
        System.out.println(fakeCharset.encodeInt(8, 1));

        int x = 564564;
        int x2 = 42433;

        System.out.println("Original 1 -> " + x);
        System.out.println("Original 2 -> " + x2);

        String enc1 = fakeCharset.encodeInt(x, 3);
        String enc2 = fakeCharset.encodeInt(x2, 3);

        System.out.println("Encoded 1 -> " + enc1);
        System.out.println("Encoded 2 -> " + enc2);

        long dec1 = fakeCharset.decodeString(enc1);
        long dec2 = fakeCharset.decodeString(enc2);

        System.out.println("Decoded 1 -> " + dec1);
        System.out.println("Decoded 2 -> " + dec2);

        HPacket packet = new HPacket(0, fakeCharset.intToChar);
        System.out.println(packet.readString().equals(fakeCharset.intToChar));





    }
}
