package com.kotwicka.shopwithme.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kotwicka.shopwithme.persistence.entity.ShoppingList;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ShoppingListDao {

    @Insert
    void insertShoppingList(final ShoppingList shoppingList);

    @Update
    void updateShoppingList(final ShoppingList shoppingList);

    @Query("SELECT * FROM shopping_list WHERE is_archived == false ORDER BY creation_date")
    Flowable<List<ShoppingList>> getActiveShoppingLists();

    @Query("SELECT * FROM shopping_list WHERE is_archived == true ORDER BY creation_date")
    Flowable<List<ShoppingList>> getArchivedShoppingLists();
}
