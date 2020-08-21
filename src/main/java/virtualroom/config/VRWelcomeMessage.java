package virtualroom.config;

import misc.Constants;
import misc.TextReader;

public class VRWelcomeMessage {

    private String welcomeMessage;

    private VRWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }


    public static VRWelcomeMessage parse(TextReader reader) {
        return new VRWelcomeMessage(VRUtils.readVarString(reader, 2));
    }
}
