package com.kotwicka.shopwithme.shoppingitems.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.kotwicka.shopwithme.R;
import com.kotwicka.shopwithme.shoppingitems.model.ShoppingItemStub;
import com.kotwicka.shopwithme.shoppingitems.model.ShoppingItemViewModel;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnAcceptItemButtonClickedListener {
        void saveShoppingItem(final String name);
    }

    private final OnAcceptItemButtonClickedListener listener;
    private final List<ShoppingItemViewModel> shoppingItems;

    public ShoppingItemAdapter(OnAcceptItemButtonClickedListener listener) {
        this.listener = listener;
        this.shoppingItems = Lists.newArrayList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == ViewHolderType.STUB.getType()) {
            return new StubItemViewHolder(layoutInflater.inflate(R.layout.shopping_item_stub_element, parent, false));
        }
        return new ItemViewHolder(layoutInflater.inflate(R.layout.shopping_item_element, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == ViewHolderType.ITEM.getType()) {
            ((ItemViewHolder) holder).bind(shoppingItems.get(position));
        } else {
            ((StubItemViewHolder) holder).bind(listener);
        }
    }

    public void setShoppingItems(final List<ShoppingItemViewModel> newShoppingItems, final boolean isArchived) {
        shoppingItems.clear();
        notifyDataSetChanged();
        if (!isArchived) {
            addStubItem();
        }
        for (ShoppingItemViewModel shoppingItem : newShoppingItems) {
            add(shoppingItem);
        }
    }

    public void add(final ShoppingItemViewModel item) {
        final int index = shoppingItems.size() - 1 > 0 ? shoppingItems.size() - 1 : 0;
        shoppingItems.add(index, item);
        notifyItemInserted(index);
    }

    @Override
    public int getItemCount() {
        return shoppingItems.size();
    }

    public ShoppingItemViewModel get(final int position) {
        return shoppingItems.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return shoppingItems.get(position) instanceof ShoppingItemStub ? ViewHolderType.STUB.getType() : ViewHolderType.ITEM.getType();
    }


    private void addStubItem() {
        shoppingItems.add(new ShoppingItemStub());
        notifyItemInserted(shoppingItems.size() - 1);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_item_name)
        TextView listItemNameTextView;

        @BindView(R.id.shopping_item_element_foreground_view_ll)
        LinearLayout foregroundView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final ShoppingItemViewModel shoppingListItemViewModel) {
            listItemNameTextView.setText(shoppingListItemViewModel.getName());
        }

        public LinearLayout getForegroundView() {
            return foregroundView;
        }
    }

    public static class StubItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.list_item_stub)
        EditText listItemStubEditText;

        @BindView(R.id.list_item_save_btn)
        Button button;

        public StubItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final OnAcceptItemButtonClickedListener listener) {
            listItemStubEditText.requestFocus();
            listItemStubEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (StringUtils.isNotBlank(s.toString())) {
                        button.setVisibility(View.VISIBLE);
                    } else {
                        button.setVisibility(View.INVISIBLE);
                    }
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.saveShoppingItem(listItemStubEditText.getText().toString());
                    listItemStubEditText.setText(StringUtils.EMPTY);
                }
            });
        }
    }
}
