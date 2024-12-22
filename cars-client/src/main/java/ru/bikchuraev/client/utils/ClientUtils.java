package ru.bikchuraev.client.utils;

import java.awt.*;

/**
 * Класс статических утилитных методов.
 */
public final class ClientUtils {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private ClientUtils() {
    }

    /**
     * Получение размеров окна с зазорами по ширине и длине отностиельно размеров экрана монитора.
     *
     * @param widthShift  зазор по ширине
     * @param heightShift зазор по высоте
     * @return размеры окна
     */
    public static Dimension prepareWindowSizeWithShifts(int widthShift, int heightShift) {
        return new Dimension(
                (int) SCREEN_SIZE.getWidth() - widthShift,
                (int) SCREEN_SIZE.getHeight() - heightShift
        );
    }

    public static String toStringSafe(Integer value) {
        return value == null ? null : value.toString();
    }

    public static boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isInteger(String value) {
        if (isBlank(value)) {return true;}
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

}
