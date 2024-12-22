package ru.bikchuraev.server.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ServerUtils {

    public static boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }
}
