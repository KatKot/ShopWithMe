package com.kotwicka.shopwithme.shoppinglists.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import com.kotwicka.shopwithme.R;

public class AddNewShoppingListDialog extends DialogFragment {

    public interface OnClickAddNewShoppingListListener {
        void onAddNewListClick(View view);
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
                .setPositiveButton(R.string.add_new_shopping_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onAddNewListClick(dialogView);
                    }
                })
                .setNegativeButton(R.string.cancel_adding_new_list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddNewShoppingListDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
