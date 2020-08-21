package controller;

import java.util.Random;

public class SeedTest {

    public static void main(String[] args) {

        Random colorTileRand = new Random();

        // this program verifies that having a seed of
        // 5 wired rolls of a color tile (switching between 4 states)
        // results in a good random function. In habbo terms:
        // with one wired stack changing the color of a tile every 0.5s, we become a good consistent random seed,
        // this takes 2.5s to finish
        // which is about the same as an actual dice roll

        // in actual program, we'll add up the dice ID to the seed

        int range = 4;      // color tiles rand states
        int amount = 5;     // amount of colortiles rolls

        int dicePossibilities = 6;

        int[] resultSet = new int[6];


        for (int i = 0; i < 1000000; i++) {
            int seed = 0;
            for (int c = 0; c < amount; c++) {
                seed *= range;
                seed += colorTileRand.nextInt(range);
            }


            Random result = new Random(seed + 100000000);
            resultSet[result.nextInt(6)]++;
        }

        for (int i = 0; i < dicePossibilities; i++) {
            System.out.println(i + " -> " + resultSet[i]);
        }
    }

}
