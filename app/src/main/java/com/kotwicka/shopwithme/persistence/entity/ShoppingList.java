package com.kotwicka.shopwithme.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.joda.time.DateTime;

@Entity(tableName = "shopping_list")
public final class ShoppingList {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "creation_date")
    private DateTime creationDate;

    @ColumnInfo(name = "is_archived")
    private boolean isArchived;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }
}
