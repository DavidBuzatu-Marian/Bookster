package com.davidmarian_buzatu.bookster.activity.ui.search.helper;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;

public class DateFormater {
    private static DateFormater mDateFormater;

    private DateFormater() {
        mDateFormater = this;
    }

    public static DateFormater getInstance() {
        if (mDateFormater == null) {
            mDateFormater = new DateFormater();
        }
        return mDateFormater;
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
