package fakecharsets;

public interface FakeCharSet {

    int getRange();

    String encodeInt(long number, int length);

    long decodeString(String compressed);

}
