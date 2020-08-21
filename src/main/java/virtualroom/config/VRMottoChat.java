package virtualroom.config;

import fakecharsets.FakeCharSet;
import misc.Constants;
import misc.TextReader;

public class VRMottoChat {

    private final boolean useMottoChat;

    private VRMottoChat(boolean useMottoChat) {
        this.useMottoChat = useMottoChat;
    }

    public boolean useMottoChat() {
        return useMottoChat;
    }


    public static VRMottoChat parse(TextReader reader) {
        return new VRMottoChat(VRUtils.readBoolean(reader));
    }
}
