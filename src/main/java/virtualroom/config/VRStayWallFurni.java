package virtualroom.config;

import fakecharsets.FakeCharSet;
import misc.Constants;
import misc.TextReader;

import java.util.HashSet;
import java.util.Set;

public class VRStayWallFurni {

    private Set<Integer> stayWallFurni;

    private VRStayWallFurni(Set<Integer> stayWallFurni) {
        this.stayWallFurni = stayWallFurni;
    }

    public Set<Integer> getStayWallFurni() {
        return stayWallFurni;
    }

    public static VRStayWallFurni parse(TextReader reader) {
        Set<Integer> stayWallFurniIds = new HashSet<>();

        int amount = VRUtils.readInteger(reader, 2, false);
        for (int i = 0; i < amount; i++) {
            int id = VRUtils.readInteger(reader, 6, false);
            stayWallFurniIds.add(id);
        }

        return new VRStayWallFurni(stayWallFurniIds);
    }
}

