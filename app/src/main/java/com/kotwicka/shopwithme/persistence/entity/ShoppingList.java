package com.kotwicka.shopwithme.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.joda.time.LocalDate;

@Entity(tableName = "shopping_list")
public final class ShoppingList {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private final long id;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "creation_date")
    private final LocalDate creationDate;

    @ColumnInfo(name = "is_archived")
    private final boolean isArchived;

    public ShoppingList(final long id, final String name, final LocalDate creationDate, final boolean isArchived) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.isArchived = isArchived;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public boolean isArchived() {
        return isArchived;
    }
}
