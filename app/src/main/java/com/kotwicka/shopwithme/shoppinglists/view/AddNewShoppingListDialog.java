package com.kotwicka.shopwithme.shoppinglists.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kotwicka.shopwithme.R;

public class AddNewShoppingListDialog extends DialogFragment {

    public interface OnClickAddNewShoppingListListener {
        void onAddNewListClick(View view);

        void onInputChanged(String name, View view);
    }

    private OnClickAddNewShoppingListListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnClickAddNewShoppingListListener) context;
        } catch (ClassCastException e) {

        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_add_shopping_list, null);
        builder.setView(dialogView)
                .setPositiveButton(R.string.add_new_shopping_list, null)
                .setNegativeButton(R.string.cancel_adding_new_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddNewShoppingListDialog.this.getDialog().cancel();
                    }
                });
        final EditText editText = dialogView.findViewById(R.id.shopping_list_name_et);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onInputChanged(s.toString(), dialogView);
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onAddNewListClick(dialogView);
                    }
                });
            }
        });
        return dialog;
    }
}
