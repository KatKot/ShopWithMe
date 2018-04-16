package com.kotwicka.shopwithme.shoppinglists.view;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.kotwicka.shopwithme.R;
import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements AddNewShoppingListDialog.OnClickAddNewShoppingListListener, ShoppingListContract.View {

    @Inject
    ShoppingListContract.Presenter presenter;

    private TextInputLayout addNewListTextInputLayout;
    private EditText addNewListEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showAddNewListDialog(final View view){
        final AddNewShoppingListDialog dialog = new AddNewShoppingListDialog();
        dialog.show(getSupportFragmentManager(), getString(R.string.add_list_dialog_fragment_tag));
    }

    @Override
    public void onAddNewListClick(final View view) {
        this.addNewListTextInputLayout =  view.findViewById(R.id.shopping_list_name_til);
        this.addNewListEditText = view.findViewById(R.id.shopping_list_name_et);
        this.addNewListEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.validateShoppingListName(s.toString());
            }
        });
        final String newListName = addNewListEditText.getText().toString();
        presenter.saveShoppingList(newListName);
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
    public void onNewShoppingListSaved() {

    }
}
