package vn.com.dpm.common.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;


public final class CronUtils {

    private static final String ALL = "*";
    private static final String ANY = "?";

    private CronUtils() {

    }

    public static String toCronDaily(LocalTime daily) {
        return toCron(CronField.builder()
                .second(String.valueOf(daily.getSecond()))
                .minute(String.valueOf(daily.getMinute()))
                .hour(String.valueOf(daily.getHour()))
                .dayOfMonth(ALL)
                .month(ALL)
                .dayOfWeek(ANY)
                .build());
    }

    private static String toCron(CronField field) {
        return String.format("%s %s %s %s %s %s",
                field.getSecond(),
                field.getMinute(),
                field.getHour(),
                field.getDayOfMonth(),
                field.getMonth(),
                field.getDayOfWeek()
        );
    }

    @Getter
    @Builder
    @FieldDefaults(makeFinal = true)
    static class CronField {
        private String second;
        private String minute;
        private String hour;
        private String dayOfMonth;
        private String month;
        private String dayOfWeek;
    }

}
