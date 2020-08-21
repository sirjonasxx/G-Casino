package virtualroom.config.generator;

import gearth.extensions.parsers.HPoint;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigTextTest {


    private static List<FakeWallItem> generateWalls() {
        List<FakeWallItem> fakeWallItems = new ArrayList<>();


        // kerstversiering links -----------
        int x = 3;
        for (int y = 2; y < 13; y++) {
            for (int l2 = 107; l2 > 10; l2 -= 16) {
                FakeWallItem fakeWallItem = new FakeWallItem(
                        4001,
                        new HPoint(x, y),
                        30, l2,
                        "l",
                        "27"
                );

                fakeWallItems.add(fakeWallItem);
            }
        }
        // --------------------------------

        // kerstversiering rechts ---------
        int y = 0;
        for (x = 4; x < 10; x++) {
            for (int l2 = 122; l2 > 15; l2 -= 16) {
                FakeWallItem fakeWallItem = new FakeWallItem(
                        4001,
                        new HPoint(x, y),
                        31, l2,
                        "r",
                        "27"
                );

                fakeWallItems.add(fakeWallItem);
            }
        }
        // --------------------------------

        // eid mubarak
        FakeWallItem eid = new FakeWallItem(
                4001,
                new HPoint(10, 1),
                21, -87,
                "r",
                "1004"
        );
        fakeWallItems.add(eid);

        FakeWallItem infobusleft = new FakeWallItem(
                4001,
                new HPoint(4, 12),
                8, -99,
                "l",
                "2005"
        );
        fakeWallItems.add(infobusleft);

        FakeWallItem infobusright = new FakeWallItem(
                4001,
                new HPoint(4, 7),
                8, -99,
                "l",
                "2005"
        );
        fakeWallItems.add(infobusright);


        FakeWallItem moodLight = new FakeWallItem(
                4027,
                new HPoint(4, 5),
                20, -40, "l",
                "2,1,2,#000000,0"
        );
        fakeWallItems.add(moodLight);


        return fakeWallItems;
    }
    private static List<FloorItemMap> generateMappings() {
        List<FloorItemMap> floorItemMaps = new ArrayList<>(Arrays.asList(
                new FloorItemMap(229298165, 1551, 0.0), // laser
                new FloorItemMap(51196793, 1635, 0.74), // p droomijs

                // dobbels:
                new FloorItemMap(229392434, 284, 0.0),
                new FloorItemMap(229392435, 284, 0.5),
                new FloorItemMap(229392436, 284, 0.5),
                new FloorItemMap(229891181, 284, 0.5),
                new FloorItemMap(234979663, 284, 0.0),

                new FloorItemMap(265807276, 284, 0.0),
                new FloorItemMap(265807277, 284, 0.5),
                new FloorItemMap(265807298, 284, 0.5),
                new FloorItemMap(282735135, 284, 0.5),
                new FloorItemMap(282735136, 284, 0.0),

                new FloorItemMap(282735133, 284, 0.0),
                new FloorItemMap(282735134, 284, 0.5),
                new FloorItemMap(282735139, 284, 0.5),
                new FloorItemMap(282735140, 284, 0.5),
                new FloorItemMap(282735137, 284, 0.0),

                new FloorItemMap(282735138, 284, 0.0),
                new FloorItemMap(282735143, 284, 0.5),
                new FloorItemMap(282735144, 284, 0.5),
                new FloorItemMap(282735141, 284, 0.5),
                new FloorItemMap(282735142, 284, 0.0)


        ));

        return floorItemMaps;
    }

    private static List<FakeFloorItem> createPetalPatches() {
        List<FakeFloorItem> petalPatches = new ArrayList<>();

        for (int x = 4; x < 11; x += 2) {
            for (int y = 1; y < 12; y += 2) {
                FakeFloorItem fakeFloorItem = new FakeFloorItem(
                        285,
                        new HPoint(x, y, 0.0),
                        0,
                        ""
                );

                petalPatches.add(fakeFloorItem);
            }
        }

        for (int x = 4; x < 11; x += 2) {
            FakeFloorItem fakeFloorItem = new FakeFloorItem(
                    285,
                    new HPoint(x, 12, 0.0),
                    0,
                    ""
            );

            petalPatches.add(fakeFloorItem);
        }


        return petalPatches;
    }
    private static List<FakeFloorItem> createSeats() {
        List<FakeFloorItem> seats = new ArrayList<>();

        // hobba thrones
        seats.addAll(Arrays.asList(
                new FakeFloorItem(230, new HPoint(11, 12, 0.2), 2, ""),
                new FakeFloorItem(230, new HPoint(11, 9, 0.2), 2, ""),
                new FakeFloorItem(230, new HPoint(11, 6, 0.2), 2, ""),
                new FakeFloorItem(230, new HPoint(11, 3, 0.2), 2, "")
        ));

        // lobby seats
        seats.addAll(Arrays.asList(
                new FakeFloorItem(1584, new HPoint(5, 13, 0.3), 4, ""),
                new FakeFloorItem(1584, new HPoint(6, 13, 0.3), 4, ""),
                new FakeFloorItem(1584, new HPoint(7, 13, 0.3), 4, ""),

                new FakeFloorItem(1584, new HPoint(4, 11, 0.3), 2, ""),
                new FakeFloorItem(1584, new HPoint(4, 12, 0.3), 2, ""),

                new FakeFloorItem(1584, new HPoint(8, 6, 0.3), 2, ""),
                new FakeFloorItem(1584, new HPoint(8, 7, 0.3), 2, ""),
                new FakeFloorItem(1584, new HPoint(8, 8, 0.3), 2, ""),
                new FakeFloorItem(1584, new HPoint(8, 9, 0.3), 2, ""),
                new FakeFloorItem(1584, new HPoint(8, 10, 0.3), 2, ""),
                new FakeFloorItem(1584, new HPoint(8, 11, 0.3), 2, ""),
                new FakeFloorItem(1584, new HPoint(8, 12, 0.3), 2, ""),

                new FakeFloorItem(1584, new HPoint(4, 6, 0.3), 2, ""),
                new FakeFloorItem(1584, new HPoint(4, 7, 0.3), 2, ""),

                new FakeFloorItem(1584, new HPoint(5, 5, 0.3), 4, ""),
                new FakeFloorItem(1584, new HPoint(6, 5, 0.3), 4, ""),
                new FakeFloorItem(1584, new HPoint(7, 5, 0.3), 4, ""),

                new FakeFloorItem(1584, new HPoint(5, 1, 0.3), 4, ""),
                new FakeFloorItem(1584, new HPoint(6, 1, 0.3), 4, ""),
                new FakeFloorItem(1584, new HPoint(7, 1, 0.3), 4, ""),

                new FakeFloorItem(1584, new HPoint(4, 2, 0.3), 2, ""),
                new FakeFloorItem(1584, new HPoint(4, 3, 0.3), 2, ""),

                new FakeFloorItem(1584, new HPoint(8, 2, 0.3), 2, ""),
                new FakeFloorItem(1584, new HPoint(8, 3, 0.3), 2, "")
        ));

        return seats;
    }
    private static List<FakeFloorItem> createLayout() {
        List<FakeFloorItem> all = new ArrayList<>();


        List<Triple<HPoint, Double, Integer>> stackinoEggs = new ArrayList<>(Arrays.asList(
                new ImmutableTriple<>(new HPoint(8, 13), 0.0, 2),
//                new ImmutableTriple<>(new HPoint(8, 5), 0.0, 2),
                new ImmutableTriple<>(new HPoint(5, 4),0.0, 1),
                new ImmutableTriple<>(new HPoint(6, 4),0.0, 1),
                new ImmutableTriple<>(new HPoint(7, 4),0.0, 1),
                new ImmutableTriple<>(new HPoint(8, 4),0.0, 1),
                new ImmutableTriple<>(new HPoint(4, 13),0.0, 10),
                new ImmutableTriple<>(new HPoint(4, 10),0.0, 10),
                new ImmutableTriple<>(new HPoint(4, 8), 0.0,10),
                new ImmutableTriple<>(new HPoint(4, 4),0.0,14),
                new ImmutableTriple<>(new HPoint(4, 1),0.0,14),
                new ImmutableTriple<>(new HPoint(8, 1),0.0,14),
                new ImmutableTriple<>(new HPoint(11, 1),0.0, 10),

                new ImmutableTriple<>(new HPoint(4, 3),6.0,2),
                new ImmutableTriple<>(new HPoint(4, 2),6.0,2),
                new ImmutableTriple<>(new HPoint(5, 1),6.0,2),
                new ImmutableTriple<>(new HPoint(6, 1),6.0,2),
                new ImmutableTriple<>(new HPoint(7, 1),6.0,2)
        ));

        for (int y = 2; y < 14; y++) {
            stackinoEggs.add(new ImmutableTriple<>(new HPoint(9, y), 0.0, 2));
        }

        for (int y = 1; y < 4; y++) {
            stackinoEggs.add(new ImmutableTriple<>(new HPoint(4, y), 3.0, 2));
        }

        for (int y = 4; y < 14; y++) {
            stackinoEggs.add(new ImmutableTriple<>(new HPoint(4, y), 4.0, 2));
        }

        for (int x = 5; x < 8; x++) {
            stackinoEggs.add(new ImmutableTriple<>(new HPoint(x, 1), 3.0, 2));
        }

        for (int x = 8; x < 12; x++) {
            stackinoEggs.add(new ImmutableTriple<>(new HPoint(x, 1), 4.0, 2));
        }

        for (Triple<HPoint, Double, Integer> pair : stackinoEggs) {
            HPoint point = pair.getLeft();
            double offset = pair.getMiddle();
            int stack = pair.getRight();
            for (int i = 0; i < stack; i++) {
                FakeFloorItem dinoEgg = new FakeFloorItem(
                        260,
                        new HPoint(point.getX(), point.getY(), (((double)i)/2) + offset),
                        0,
                        ""
                );

                all.add(dinoEgg);
            }
        }

        //goldbars
        all.add(new FakeFloorItem(2067, new HPoint(4, 5), 0, ""));
        all.add(new FakeFloorItem(2067, new HPoint(4, 5, 0.37), 0, ""));

        all.add(new FakeFloorItem(2067, new HPoint(8, 5), 0, ""));
        all.add(new FakeFloorItem(2067, new HPoint(8, 5, 0.37), 0, ""));


//        all.add(new FakeFloorItem(2067, new HPoint(6, 8, 0.1), 0, ""));
//        all.add(new FakeFloorItem(2067, new HPoint(6, 10, 0.1), 0, ""));
//        all.add(new FakeFloorItem(2067, new HPoint(6, 9, 0.1), 0, ""));
//        all.add(new FakeFloorItem(2067, new HPoint(6, 9, 0.47), 0, ""));



        // "gouden beker"
        all.add(new FakeFloorItem(185, new HPoint(8, 5, 0.74), 2, "1"));


        ////////////// dealer corner
        // natuurbed
        all.add(new FakeFloorItem(3317, new HPoint(4, 2, 3.8), 0, ""));
        all.add(new FakeFloorItem(3317, new HPoint(4, 3, 3.8), 0, ""));
        all.add(new FakeFloorItem(3317, new HPoint(5, 1, 3.8), 0, ""));
        all.add(new FakeFloorItem(3317, new HPoint(6, 1, 3.8), 0, ""));
        all.add(new FakeFloorItem(3317, new HPoint(7, 1, 3.8), 0, ""));

        // sierboeket
        all.add(new FakeFloorItem(3209, new HPoint(4, 2, 3.95), 0, ""));
        all.add(new FakeFloorItem(3209, new HPoint(4, 3, 3.95), 0, ""));
        all.add(new FakeFloorItem(3209, new HPoint(5, 1, 3.95), 0, ""));
        all.add(new FakeFloorItem(3209, new HPoint(6, 1, 3.95), 0, ""));
        all.add(new FakeFloorItem(3209, new HPoint(7, 1, 3.95), 0, ""));

        // dragons
        all.add(new FakeFloorItem(4553, new HPoint(4, 2, 4), 2, ""));
        all.add(new FakeFloorItem(1624, new HPoint(4, 3, 4), 2, ""));
        all.add(new FakeFloorItem(1624, new HPoint(5, 1, 4), 4, ""));
        all.add(new FakeFloorItem(4553, new HPoint(6, 1, 4), 4, ""));
        all.add(new FakeFloorItem(1624, new HPoint(7, 1, 4), 4, ""));


        return all;
    }

    private static List<FakeFloorItem> generateFloorFurniture() {
        List<FakeFloorItem> fakeFloorItems = new ArrayList<>();
        fakeFloorItems.addAll(createPetalPatches());
        fakeFloorItems.addAll(createSeats());
        fakeFloorItems.addAll(createLayout());

        return fakeFloorItems;
    }

    private static String heightmap() {
        return "xxxxxxxxxxxx\r" +
                "xxxx00000000\r" +
                "xxxx00000000\r" +
                "xxxx00000000\r" +
                "xxxx00000000\r" +
                "xxx000000000\r" +
                "xxxx00000000\r" +
                "xxxx00000000\r" +
                "xxxx00000000\r" +
                "xxxx00000000\r" +
                "xxxx00000000\r" +
                "xxxx00000000\r" +
                "xxxx00000000\r" +
                "xxxx00000000\r" +
                "xxxxxxxxxxxx\r" +
                "xxxxxxxxxxxx\r";
    }

    public static String generate() {
        ConfigTextGenerator configTextGenerator = new ConfigTextGenerator();
        configTextGenerator.appendVersion();
        configTextGenerator.appendWallFurniture(generateWalls());
        configTextGenerator.appendMapping(generateMappings());
        configTextGenerator.appendFloorFurniture(generateFloorFurniture());
        configTextGenerator.appendThickness(true, 0, -2);
        configTextGenerator.appendHeightmap(heightmap());
        configTextGenerator.appendInitDoubleClickFurni(248776570);
        configTextGenerator.appendWelcomeMessage("Don't forget to join the G-Earth " +
                "<a href=\"https://discord.gg/AVkcF8y\" target=\"_blank\">Discord server</a> " +
                "for updates on this project!");
        configTextGenerator.appendMottoChat(true);
//        configTextGenerator.appendRoomPaint("304", "107", "");
        configTextGenerator.appendSeed(217473316, 4, 5);
        configTextGenerator.appendShoutSuppress(true);


        System.out.println(configTextGenerator.toString().length());

        return configTextGenerator.toString();
    }

}
