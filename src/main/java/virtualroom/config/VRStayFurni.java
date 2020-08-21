package virtualroom.config;

import fakecharsets.FakeCharSet;
import misc.Constants;
import misc.TextReader;

import java.util.HashSet;
import java.util.Set;

public class VRStayFurni {

    private Set<Integer> stayFurniIds;

    private VRStayFurni(Set<Integer> stayFurniIds) {
        this.stayFurniIds = stayFurniIds;
    }

    public Set<Integer> getStayFurniIds() {
        return stayFurniIds;
    }

    public static VRStayFurni parse(TextReader reader) {
        Set<Integer> stayFurniIds = new HashSet<>();

        int amount = VRUtils.readInteger(reader, 2, false);
        for (int i = 0; i < amount; i++) {
            int id = VRUtils.readInteger(reader, 6, false);
            stayFurniIds.add(id);
        }

        return new VRStayFurni(stayFurniIds);
    }
}
