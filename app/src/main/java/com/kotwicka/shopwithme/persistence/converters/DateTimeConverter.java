package com.kotwicka.shopwithme.persistence.converters;

import android.arch.persistence.room.TypeConverter;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public final class DateTimeConverter {

    private DateTimeConverter() {

    }

    @TypeConverter
    public static DateTime fromLong(final long date) {
        return new DateTime(date);
    }

    @TypeConverter
    public static long toLong(final DateTime date) {
        return date.toDate().getTime();
    }
}
