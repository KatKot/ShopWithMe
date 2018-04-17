package com.kotwicka.shopwithme.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kotwicka.shopwithme.persistence.entity.ShoppingList;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface ShoppingListDao {

    @Insert
    long insertShoppingList(final ShoppingList shoppingList);

    @Update
    void updateShoppingList(final ShoppingList shoppingList);

    @Query("SELECT * FROM shopping_list WHERE id == :id LIMIT 1")
    Maybe<ShoppingList> getShoppingList(final long id);

    @Query("SELECT * FROM shopping_list WHERE is_archived == 0 ORDER BY creation_date DESC")
    Flowable<List<ShoppingList>> getActiveShoppingLists();

    @Query("SELECT * FROM shopping_list WHERE is_archived == 1 ORDER BY creation_date DESC")
    Flowable<List<ShoppingList>> getArchivedShoppingLists();
}
