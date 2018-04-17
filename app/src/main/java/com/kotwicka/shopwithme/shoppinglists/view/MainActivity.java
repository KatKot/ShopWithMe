package com.kotwicka.shopwithme.shoppinglists.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kotwicka.shopwithme.R;
import com.kotwicka.shopwithme.app.ShopWithMeApp;
import com.kotwicka.shopwithme.shoppingitems.view.ShoppingItemsActivity;
import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListViewModel;
import com.kotwicka.shopwithme.shoppinglists.view.adapter.ShoppingListAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AddNewShoppingListDialog.OnClickAddNewShoppingListListener, ShoppingListAdapter.OnShoppingListClickedListener, ShoppingListContract.View {

    @BindView(R.id.shopping_lists_rv)
    RecyclerView recyclerView;

    @Inject
    ShoppingListContract.Presenter presenter;

    private TextInputLayout addNewListTextInputLayout;
    private EditText addNewListEditText;
    private AddNewShoppingListDialog addNewShoppingListDialog;

    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ShopWithMeApp.get().withShoppingListComponent(this).inject(this);
        initializeViews();
        fetchShoppingLists();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetachView();
        ShopWithMeApp.get().clearShoppingListComponent();
    }

    private void initializeViews() {
        shoppingListAdapter = new ShoppingListAdapter(this);
        recyclerView.setAdapter(shoppingListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void fetchShoppingLists() {
        Log.d("MAinActivity", "Fetching...");
        presenter.fetchActiveShoppingLists();
    }

    public void showAddNewListDialog(final View view) {
        addNewShoppingListDialog = new AddNewShoppingListDialog();
        addNewShoppingListDialog.show(getSupportFragmentManager(), getString(R.string.add_list_dialog_fragment_tag));
    }

    @Override
    public void onAddNewListClick(final View view) {
        findInputsFromDialog(view);
        final String newListName = addNewListEditText.getText().toString();
        presenter.saveShoppingList(newListName);
    }

    @Override
    public void onInputChanged(final String name, final View view) {
        findInputsFromDialog(view);
        presenter.isValidShoppingList(name);
    }

    private void findInputsFromDialog(final View view) {
        this.addNewListTextInputLayout = view.findViewById(R.id.shopping_list_name_til);
        this.addNewListEditText = view.findViewById(R.id.shopping_list_name_et);
    }

    @Override
    public void showEmptyListNameError() {
        addNewListTextInputLayout.setError(getString(R.string.empty_list_name_error));
    }

    @Override
    public void clearListNameError() {
        addNewListTextInputLayout.setErrorEnabled(false);
    }

    @Override
    public void onNewShoppingListSaved(final long id, final String name) {
        addNewShoppingListDialog.dismiss();
        startShoppingItemActivity(name, id);
    }

    @Override
    public void showSaveListErrorMessage() {

    }

    @Override
    public void setShoppingLists(List<ShoppingListViewModel> shoppingListViewModel) {
        Log.d("MAinActivity", "Shopping lists size : " + shoppingListViewModel.size());
        shoppingListAdapter.setLists(shoppingListViewModel);
    }

    @Override
    public void onShoppingListClicked(final String name, final long id) {
        startShoppingItemActivity(name, id);
    }

    private void startShoppingItemActivity(final String name, final long id) {
        final Intent intent = new Intent(this, ShoppingItemsActivity.class);
        intent.putExtra(getString(R.string.shopping_list_id_extra), id);
        intent.putExtra(getString(R.string.shopping_list_name_extra), name);
        startActivity(intent);
    }
}
