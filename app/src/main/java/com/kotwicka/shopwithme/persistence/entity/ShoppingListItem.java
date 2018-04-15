package com.kotwicka.shopwithme.persistence.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "shopping_list_item",
        indices = @Index("list_id"),
        foreignKeys = @ForeignKey(entity = ShoppingList.class,
                parentColumns = "id",
                childColumns = "shopping_list_id",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE))
public final class ShoppingListItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private final long id;

    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "shopping_list_id")
    private final long shoppingListId;

    public ShoppingListItem(final long id, final String name, final long listId) {
        this.id = id;
        this.name = name;
        this.shoppingListId = listId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getShoppingListId() {
        return shoppingListId;
    }
}
