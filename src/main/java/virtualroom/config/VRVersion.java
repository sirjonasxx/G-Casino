package virtualroom.config;

import misc.Constants;
import misc.TextReader;

public class VRVersion {

    private String version;

    private VRVersion(String version) {
        this.version = version;
    }

    public boolean isValid() {
        return version.equals(Constants.version);
    }


    public static VRVersion parse(TextReader reader) {
        return new VRVersion(VRUtils.readVarString(reader, 1));
    }
}
