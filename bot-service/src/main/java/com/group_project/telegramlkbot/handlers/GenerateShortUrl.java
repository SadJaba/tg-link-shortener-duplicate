package com.group_project.telegramlkbot.handlers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GenerateShortUrl {
    public static String generate() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 7; i++) {
            int index = (int) (Math.random() * characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();

    }
}
