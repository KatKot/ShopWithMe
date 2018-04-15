package com.kotwicka.shopwithme.persistence.converters;

import android.arch.persistence.room.TypeConverter;

import org.joda.time.LocalDate;

public final class LocalDateConverter {

    private LocalDateConverter() {

    }

    @TypeConverter
    public static LocalDate fromLong(final long date) {
        return new LocalDate(date);
    }

    @TypeConverter
    public static long toLong(final LocalDate date) {
        return date.toDate().getTime();
    }
}
