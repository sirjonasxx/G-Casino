package virtualroom.config;

import misc.Constants;
import misc.TextReader;

import java.util.ArrayList;
import java.util.List;

public class VRParser {

    public static List<Object> parse(String s) {
        try {
            List<Object> results = new ArrayList<>();
            TextReader reader = new TextReader(s, 0);

            String startVal = Constants.key + "\n\n";
            if (!s.startsWith(startVal)) {
                return null;
            }

            reader.read(startVal.length());

            String type = reader.read(2);
            while(!reader.isEOF()) {

                if (type.equals("1.")) {
                    results.add(VRVersion.parse(reader));
                }
                else if (type.equals("2.")) {
                    results.add(VRStayFurni.parse(reader));
                }
                else if (type.equals("3.")) {
                    results.add(VRMapFurni.parse(reader));
                }
                else if (type.equals("4.")) {
                    results.add(VRAddFurni.parse(reader));
                }
                else if (type.equals("5.")) {
                    results.add(VRWallFurni.parse(reader));
                }
                else if (type.equals("7.")) {
                    results.add(VRThickness.parse(reader));
                }
                else if (type.equals("8.")) {
                    results.add(VRHeightMap.parse(reader));
                }
                else if (type.equals("9.")) {
                    results.add(VRInitDoubleClickFurni.parse(reader));
                }
                else if (type.equals("a.")) {
                    results.add(VRWelcomeMessage.parse(reader));
                }
                else if (type.equals("b.")) {
                    results.add(VRStayWallFurni.parse(reader));
                }
                else if (type.equals("c.")) {
                    results.add(VRMottoChat.parse(reader));
                }
                else if (type.equals("d.")) {
                    results.add(VRRoomPaint.parse(reader));
                }
                else if (type.equals("e.")) {
                    results.add(VRSeed.parse(reader));
                }
                else if (type.equals("f.")) {
                    results.add(VRSuppressShout.parse(reader));
                }
                else {
                    return null;
                }

                if (!reader.isEOF()) {
                    reader.read(1); // newline
                    type = reader.read(2);
                }
            }

            return results;
        }
        catch (Exception e) {
            return null;
        }
    }

}
