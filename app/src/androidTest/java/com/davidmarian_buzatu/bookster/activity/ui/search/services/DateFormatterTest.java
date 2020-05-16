package com.davidmarian_buzatu.bookster.activity.ui.search.services;

import org.junit.Test;

//import java.time.temporal.ChronoUnit;
import org.threeten.bp.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class DateFormatterTest {
    private DateFormatter dateFormatter;
    private long date1,date2;
    private String pattern;


    @Test
    public void getFormattedDate() {
        dateFormatter=DateFormatter.getInstance();
        date1=System.currentTimeMillis();
        pattern="dd/MM/yyyy";
        assertEquals("16/05/2020",dateFormatter.getFormattedDate(date1,pattern));
    }

    @Test
    public void getDate() {
        dateFormatter=DateFormatter.getInstance();
        date1=1589662909;//Jan 19 1970
        date2=2097656779;//Jan 25 1970
        assertEquals(5, ChronoUnit.DAYS.between(DateFormatter.getInstance().getDate(date1),DateFormatter.getInstance().getDate(date2)));
    }
}