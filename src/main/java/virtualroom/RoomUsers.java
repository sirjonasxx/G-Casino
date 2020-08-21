package virtualroom;

import gearth.extensions.parsers.HEntity;
import gearth.extensions.parsers.HPoint;
import gearth.misc.listenerpattern.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// keeps track of user positions and mottos and provides all info to VirtualRoom
public class RoomUsers {

    private Map<Integer, HEntity> allUsers = new HashMap<>();
    private Map<Integer, HPoint> userPosition = new HashMap<>();
    private List<List<List<Integer>>> positionUser;

    private Observable<OnMottoChange> onMottoChangeObservable = new Observable<>();
    private Map<Integer, String> latestMotto = new HashMap<>();

    RoomUsers() {
        int dimension = 200;
        positionUser = new ArrayList<>();
        for (int x = 0; x < dimension; x++) {
            positionUser.add(new ArrayList<>());
            for (int y = 0; y < dimension; y++) {
                positionUser.get(x).add(new ArrayList<>());
            }
        }
    }

    void userMove(int index, HPoint newPos) {
        if (allUsers.containsKey(index)) {
            HPoint oldPos = userPosition.get(index);
            positionUser.get(oldPos.getX()).get(oldPos.getY()).remove(Integer.valueOf(index));
            positionUser.get(newPos.getX()).get(newPos.getY()).add(index);
            userPosition.put(index, newPos);
        }
    }
    void userEnter(HEntity hEntity) {
        allUsers.put(hEntity.getIndex(), hEntity);
        userPosition.put(hEntity.getIndex(), hEntity.getTile());
        positionUser.get(hEntity.getTile().getX()).get(hEntity.getTile().getY()).add(hEntity.getIndex());
        latestMotto.put(hEntity.getIndex(), hEntity.getMotto());
    }
    void userLeave(int index) {
        if (allUsers.containsKey(index)) {
            allUsers.remove(index);
            HPoint oldPos = userPosition.get(index);
            userPosition.remove(index);
            positionUser.get(oldPos.getX()).get(oldPos.getY()).remove(Integer.valueOf(index));
            latestMotto.remove(index);
        }
    }

    void userMottoMaybeUpdate(int index, String motto) {
        if (latestMotto.containsKey(index) && !latestMotto.get(index).equals(motto)) {
            // motto has updated
            latestMotto.put(index, motto);
            onMottoChangeObservable.fireEvent(onMottoChange -> onMottoChange.onMottoChange(index, motto));
        }
    }

    public HPoint getUserPosition(int index) {
        return userPosition.get(index);
    }
    public List<Integer> getUsersOnPosition(HPoint hPoint) {
        return positionUser.get(hPoint.getX()).get(hPoint.getY());
    }
    public HEntity getUserForIndex(int index) {
        return allUsers.get(index);
    }

    public Observable<OnMottoChange> getOnMottoChangeObservable() {
        return onMottoChangeObservable;
    }
}
