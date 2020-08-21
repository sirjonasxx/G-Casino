package virtualroom.config;

import misc.Constants;
import misc.TextReader;

public class VRHeightMap {

    private String heightMap;

    private VRHeightMap(String heightMap) {
        this.heightMap = heightMap;
    }

    public String getHeightMap() {
        return heightMap;
    }


    public static VRHeightMap parse(TextReader reader) {
        return new VRHeightMap(
                VRUtils.readVarString(reader, 2)
        );
    }
}
