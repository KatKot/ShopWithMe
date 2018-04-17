package com.kotwicka.shopwithme.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "shopping_list_item",
        indices = @Index("shopping_list_id"),
        foreignKeys = @ForeignKey(entity = ShoppingList.class,
                parentColumns = "id",
                childColumns = "shopping_list_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE))
public final class ShoppingListItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "shopping_list_id")
    private long shoppingListId;


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getShoppingListId() {
        return shoppingListId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setShoppingListId(long shoppingListId) {
        this.shoppingListId = shoppingListId;
    }
}
