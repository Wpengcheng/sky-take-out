package com.sky.utils;

import java.util.Random;

public class RandomNumberGenerator {



    public static long generateRandomLong(int length) {
        Random random = new Random();
        long min = (long) Math.pow(10, length - 1);
        long max = (long) Math.pow(10, length) - 1;
        return min + random.nextLong() % (max - min + 1);
    }
}