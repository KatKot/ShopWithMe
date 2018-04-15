package com.kotwicka.shopwithme.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.kotwicka.shopwithme.persistence.entity.ShoppingListItem;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ShoppingListItemDao {

    @Insert
    void insertShoppingListItem(final ShoppingListItem item);

    @Delete
    void deleteShoppingListItem(final ShoppingListItem item);

    @Query("SELECT * FROM shopping_list_item WHERE shopping_list_id == :listId")
    Flowable<List<ShoppingListItem>> getItemsForShoppingList(final long listId);

}
