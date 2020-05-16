package com.davidmarian_buzatu.bookster.services;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.test.core.app.ApplicationProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static org.junit.Assert.*;

public class OfferActionsTest {
    private OfferActions offerActions;
    private List<TextInputEditText> tiet;
    private CalendarActions calendarActions;
    private List<Bitmap> picturesList;
    private Context mContext = ApplicationProvider.getApplicationContext();

    @Test
    public void validFields() {
        offerActions=OfferActions.getInstance();
        TextInputEditText description=new TextInputEditText(mContext);
        TextInputEditText roomSize=new TextInputEditText(mContext);
        description.setText("central position, luxury hotel");
        roomSize.setText("16");
        tiet=new ArrayList<>();
        tiet.add(description);
        tiet.add(roomSize);
        calendarActions=null;
        picturesList=null;
        assertNull(OfferActions.validFields(tiet,calendarActions,picturesList,mContext));
    }
}