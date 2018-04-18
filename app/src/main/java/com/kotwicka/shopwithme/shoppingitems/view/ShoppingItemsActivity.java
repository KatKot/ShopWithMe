package com.kotwicka.shopwithme.shoppingitems.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.kotwicka.shopwithme.R;
import com.kotwicka.shopwithme.app.ShopWithMeApp;
import com.kotwicka.shopwithme.shoppingitems.adapter.ShoppingItemAdapter;
import com.kotwicka.shopwithme.shoppingitems.contract.ShoppingItemContract;
import com.kotwicka.shopwithme.shoppingitems.model.ShoppingItemViewModel;
import com.kotwicka.shopwithme.shoppingitems.view.helper.ShoppingItemTouchHelper;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingItemsActivity extends AppCompatActivity implements ShoppingItemTouchHelper.OnSwipedListener, ShoppingItemContract.View, ShoppingItemAdapter.OnAcceptItemButtonClickedListener {

    @BindView(R.id.shopping_items_rv)
    RecyclerView recyclerView;

    @Inject
    ShoppingItemContract.Presenter presenter;

    private ShoppingItemAdapter adapter;
    private ShoppingListViewModel shoppingList;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_items);

        ButterKnife.bind(this);
        ShopWithMeApp.get().withShoppingItemComponent(this).inject(this);

        this.shoppingList = getIntent().getParcelableExtra(getString(R.string.shopping_list_extra));
        getSupportActionBar().setTitle(shoppingList.getName());
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
        presenter.loadShoppingItems(shoppingList.getId());
    }

    private void initializeViews() {
        adapter = new ShoppingItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (!shoppingList.isArchived()) {
            itemTouchHelper = new ItemTouchHelper(new ShoppingItemTouchHelper(0, ItemTouchHelper.LEFT, this));
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }


    @Override
    public void saveShoppingItem(final String name) {
        presenter.saveShoppingItem(shoppingList.getId(), name);
    }

    @Override
    public void setShoppingItems(final List<ShoppingItemViewModel> shoppingItems) {
        adapter.setShoppingItems(shoppingItems, shoppingList.isArchived());
    }


    @Override
    public void onSwiped(final int position) {
        if (position < adapter.getItemCount()) {
            presenter.deleteShoppingListItem(adapter.get(position), shoppingList.getId());
        }
    }
}
