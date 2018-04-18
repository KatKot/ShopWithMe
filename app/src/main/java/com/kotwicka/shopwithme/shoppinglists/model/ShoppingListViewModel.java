package com.kotwicka.shopwithme.shoppinglists.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class ShoppingListViewModel implements Parcelable {

    private final String name;
    private final DateTime creationDate;
    private final long id;
    private final boolean isArchived;

    public ShoppingListViewModel(String name, DateTime creationDate, long id, boolean isArchived) {
        this.name = name;
        this.creationDate = creationDate;
        this.id = id;
        this.isArchived = isArchived;
    }

    protected ShoppingListViewModel(Parcel in) {
        name = in.readString();
        id = in.readLong();
        isArchived = in.readByte() != 0;
        creationDate = new DateTime(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(id);
        dest.writeByte((byte) (isArchived ? 1 : 0));
        dest.writeLong(creationDate.toDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShoppingListViewModel> CREATOR = new Creator<ShoppingListViewModel>() {
        @Override
        public ShoppingListViewModel createFromParcel(Parcel in) {
            return new ShoppingListViewModel(in);
        }

        @Override
        public ShoppingListViewModel[] newArray(int size) {
            return new ShoppingListViewModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate.toLocalDate();
    }

    public DateTime getCreationDateTime() {
        return creationDate;
    }

    public long getId() {
        return id;
    }

    public boolean isArchived() {
        return isArchived;
    }
}
