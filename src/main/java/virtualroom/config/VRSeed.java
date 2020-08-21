package virtualroom.config;

import misc.Constants;
import misc.TextReader;

public class VRSeed {

    private final int furniId;
    private final int states;
    private final int amount;

    public VRSeed(int furniId, int states, int amount) {
        this.furniId = furniId;
        this.states = states;
        this.amount = amount;
    }


    public int getFurniId() {
        return furniId;
    }

    public int getStates() {
        return states;
    }

    public int getAmount() {
        return amount;
    }


    public static VRSeed parse(TextReader reader) {
        int furniId = VRUtils.readInteger(reader, 6, false);
        int states = VRUtils.readInteger(reader, 1, false);
        int amount = VRUtils.readInteger(reader, 1, false);

        return new VRSeed(furniId, states, amount);
    }

}
