package com.davidmarian_buzatu.bookster.activity.ui.search.services;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

public class DateFormatter {
    private static DateFormatter mDateFormatter;

    private DateFormatter() {
        mDateFormatter = this;
    }

    public static DateFormatter getInstance() {
        if (mDateFormatter == null) {
            mDateFormatter = new DateFormatter();
        }
        return mDateFormatter;
    }

    public String getFormattedDate(Long date, String pattern) {
        LocalDateTime ldt = getDate(date);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        return dtf.format(ldt);
    }

    public LocalDateTime getDate(Long date) {
        Instant dateInstant = Instant.ofEpochMilli(date);
        return LocalDateTime.ofInstant(dateInstant, ZoneOffset.UTC);
    }
}
