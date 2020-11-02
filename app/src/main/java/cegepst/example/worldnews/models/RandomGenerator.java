package cegepst.example.worldnews.models;

import java.util.Random;

public class RandomGenerator {
    public static int getRandomInRange(int max, int min) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}
