package com.kotwicka.shopwithme.shoppingitems.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kotwicka.shopwithme.R;

public class ShoppingItemsActivity extends AppCompatActivity {

    private long shoppingListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_items);
        this.shoppingListId = getIntent().getLongExtra(getString(R.string.shopping_list_id_extra), 0);
        Log.d("ShoppingItemsActivity", "Got shopping list with id: " + shoppingListId);
    }
}
