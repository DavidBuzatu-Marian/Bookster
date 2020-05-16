package com.davidmarian_buzatu.bookster.services;

import com.davidmarian_buzatu.bookster.activity.ui.search.services.DateFormatter;

import org.junit.Test;

//import java.time.temporal.ChronoUnit;
import org.threeten.bp.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class DateFormatterTest {
    private DateFormatter dateFormatter;
    private long date1, date2;
    private String pattern1, pattern2, pattern3;


    @Test
    public void getFormattedDate() {
        dateFormatter = DateFormatter.getInstance();
        date1 = System.currentTimeMillis();
        pattern1 = "dd/MM/yyyy";
        pattern2 = "MM/dd/yyyy";
        pattern3 = "EEE dd MMMM YYYY";
        assertEquals("16/05/2020", dateFormatter.getFormattedDate(date1, pattern1));
        assertEquals("05/16/2020", dateFormatter.getFormattedDate(date1, pattern2));
        assertEquals("Sat 16 May 2020", dateFormatter.getFormattedDate(date1, pattern3));
    }

    @Test
    public void getDate() {
        dateFormatter = DateFormatter.getInstance();
        date1 = 1548000000;//Jan 19 1970
        date2 = 2066400000;//Jan 25 1970
        assertEquals(6, ChronoUnit.DAYS.between(DateFormatter.getInstance().getDate(date1), DateFormatter.getInstance().getDate(date2)));
        date2 = 1720800000;//Jan 21 1970
        assertEquals(2, ChronoUnit.DAYS.between(DateFormatter.getInstance().getDate(date1), DateFormatter.getInstance().getDate(date2)));
    }
}