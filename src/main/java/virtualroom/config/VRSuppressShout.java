package virtualroom.config;

import misc.TextReader;

public class VRSuppressShout {

    private final boolean suppressShout;

    private VRSuppressShout(boolean suppressShout) {
        this.suppressShout = suppressShout;
    }

    public boolean isSuppressShout() {
        return suppressShout;
    }

    public static VRSuppressShout parse(TextReader reader) {
        return new VRSuppressShout(VRUtils.readBoolean(reader));
    }

}
