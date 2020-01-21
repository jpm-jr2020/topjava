package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetweenInclusive(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        boolean startTimeOk = startTime == null || lt.compareTo(startTime) >= 0;
        boolean endTimeOk = endTime == null || lt.compareTo(endTime) <= 0;
        return  startTimeOk && endTimeOk;
    }
}
