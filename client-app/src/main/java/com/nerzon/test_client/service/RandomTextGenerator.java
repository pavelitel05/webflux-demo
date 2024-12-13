package com.nerzon.test_client.service;

import java.util.concurrent.ThreadLocalRandom;

public class RandomTextGenerator {
    private static final String SOURCE = "qwertyuiopasdfghjklzxcvbnm1234567890 qwertyuiopasdfghjklzxcvbnm";
    private static final int SOURCE_SIZE = SOURCE.length();

    public static String randomText(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
           sb.append(SOURCE.charAt(ThreadLocalRandom.current().nextInt(SOURCE_SIZE)));
        }
        return sb.toString();
    }
}
