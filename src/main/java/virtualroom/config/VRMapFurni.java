package virtualroom.config;

import fakecharsets.FakeCharSet;
import misc.Constants;
import misc.TextReader;

import java.util.HashMap;
import java.util.Map;

public class VRMapFurni {

    private Map<Integer, Integer> furniIdToTypeId;
    private Map<Integer, Double> furniIdToHeight;

    private VRMapFurni(Map<Integer, Integer> furniIdToTypeId, Map<Integer, Double> furniIdToHeight) {
        this.furniIdToTypeId = furniIdToTypeId;
        this.furniIdToHeight = furniIdToHeight;
    }

    public Map<Integer, Integer> getFurniIdToTypeId() {
        return furniIdToTypeId;
    }

    public Map<Integer, Double> getFurniIdToHeight() {
        return furniIdToHeight;
    }

    public static VRMapFurni parse(TextReader reader) {
        Map<Integer, Integer> furniIdToTypeId = new HashMap<>();
        Map<Integer, Double> furniIdToHeight = new HashMap<>();
        int amount = VRUtils.readInteger(reader, 2, false);

        for (int i = 0; i < amount; i++) {
            int furniId = VRUtils.readInteger(reader, 6, false);
            int typeId = VRUtils.readInteger(reader, 2, false);

            double z = VRUtils.readDouble(reader, true);

            furniIdToTypeId.put(furniId, typeId);
            furniIdToHeight.put(furniId, z);
        }

        return new VRMapFurni(furniIdToTypeId, furniIdToHeight);
    }

}
