package misc;

import fakecharsets.FakeCharSet;
import fakecharsets.GiftFakeCharset;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

    public static final boolean DEVELOPMODE = true;

    public static String HOTEL = "nl";
    public static final Map<String, List<Integer>> dicesTypeIdPerHotel;
    public static final Map<String, Integer> floorSwitchPerHotel;

    public static final String version = "0.1-beta";
    public static final String key = "fghfdhbljkgfjbfgfrnfgnbfnzthtzlkgbhj";

    public static final FakeCharSet giftFakeCharset;

    static {
        giftFakeCharset = new GiftFakeCharset();

        dicesTypeIdPerHotel = new HashMap<>();
        floorSwitchPerHotel = new HashMap<>();

        dicesTypeIdPerHotel.put("nl", Arrays.asList(284, 239));
        dicesTypeIdPerHotel.put("com", Arrays.asList(284, 239));

        floorSwitchPerHotel.put("nl", 3394);
        floorSwitchPerHotel.put("com", 3701);
    }





}
