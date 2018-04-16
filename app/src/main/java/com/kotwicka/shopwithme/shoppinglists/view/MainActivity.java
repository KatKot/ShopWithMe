package com.kotwicka.shopwithme.shoppinglists.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.kotwicka.shopwithme.R;
import com.kotwicka.shopwithme.app.ShopWithMeApp;
import com.kotwicka.shopwithme.shoppingitems.view.ShoppingItemsActivity;
import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements AddNewShoppingListDialog.OnClickAddNewShoppingListListener, ShoppingListContract.View {

    @Inject
    ShoppingListContract.Presenter presenter;

    private TextInputLayout addNewListTextInputLayout;
    private EditText addNewListEditText;
    private AddNewShoppingListDialog addNewShoppingListDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShopWithMeApp.get().withShoppingListComponent(this).inject(this);
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
    public void showDuplicatedListNameError() {
        addNewListTextInputLayout.setError(getString(R.string.duplicated_list_name_error));
    }

    @Override
    public void clearListNameError() {
        addNewListTextInputLayout.setErrorEnabled(false);
    }

    @Override
    public void onNewShoppingListSaved(final long id) {
        addNewShoppingListDialog.dismiss();
        final Intent intent = new Intent(this, ShoppingItemsActivity.class);
        intent.putExtra(getString(R.string.shopping_list_id_extra), id);
        startActivity(intent);
    }

    @Override
    public void showSaveListErrorMessage() {

    }
}
