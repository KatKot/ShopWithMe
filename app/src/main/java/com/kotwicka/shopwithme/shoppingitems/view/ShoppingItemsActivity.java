package com.kotwicka.shopwithme.shoppingitems.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kotwicka.shopwithme.R;
import com.kotwicka.shopwithme.app.ShopWithMeApp;
import com.kotwicka.shopwithme.shoppingitems.adapter.ShoppingItemAdapter;
import com.kotwicka.shopwithme.shoppingitems.contract.ShoppingItemContract;
import com.kotwicka.shopwithme.shoppingitems.model.ShoppingItemViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingItemsActivity extends AppCompatActivity implements ShoppingItemContract.View, ShoppingItemAdapter.OnAcceptItemButtonClickedListener {

    @BindView(R.id.shopping_items_rv)
    RecyclerView recyclerView;

    @Inject
    ShoppingItemContract.Presenter presenter;

    private ShoppingItemAdapter adapter;
    private long shoppingListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_items);

        ButterKnife.bind(this);
        ShopWithMeApp.get().withShoppingItemComponent(this).inject(this);

        this.shoppingListId = getIntent().getLongExtra(getString(R.string.shopping_list_id_extra), 0);
        getSupportActionBar().setTitle(getIntent().getStringExtra(getString(R.string.shopping_list_name_extra)));
        initializeViews();
        initializeData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetachView();
        ShopWithMeApp.get().clearShoppingItemComponent();
    }

    private void initializeData() {
        presenter.loadShoppingItems(shoppingListId);
    }

    private void initializeViews() {
        adapter = new ShoppingItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void saveShoppingItem(final String name) {
        Log.d("ShoppingItemsActivity", "Saving item with name : " + name);
        presenter.saveShoppingItem(shoppingListId, name);
    }

    @Override
    public void setShoppingItems(final List<ShoppingItemViewModel> shoppingItems) {
        Log.d("ShoppingItemsActivity", "Setting Shopping ITems " + shoppingItems.size());
        adapter.setShoppingItems(shoppingItems);
    }
}
