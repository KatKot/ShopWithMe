package com.kotwicka.shopwithme.shoppinglists.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.kotwicka.shopwithme.R;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<ShoppingListViewModel> shoppingLists;

    public ShoppingListAdapter() {
        this.shoppingLists = Lists.newArrayList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ShoppingListViewHolder(inflater.inflate(R.layout.shopping_list_element, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ShoppingListViewHolder shoppingListViewHolder = (ShoppingListViewHolder) holder;
        shoppingListViewHolder.bind(shoppingLists.get(position));
    }

    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    public void add(final ShoppingListViewModel shoppingListViewModel) {
        shoppingLists.add(shoppingListViewModel);
        notifyItemInserted(shoppingLists.size() - 1);
    }

    public void setLists(final List<ShoppingListViewModel> lists) {
        shoppingLists.clear();
        notifyDataSetChanged();
        for (ShoppingListViewModel list : lists) {
            add(list);
        }
    }

    public static class ShoppingListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.shopping_list_name)
        TextView shoppingListName;

        @BindView(R.id.shopping_list_creation_date)
        TextView shoppingListCreationDate;

        public ShoppingListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final ShoppingListViewModel shoppingListViewModel) {
            shoppingListName.setText(shoppingListViewModel.getName());
            shoppingListCreationDate.setText(shoppingListViewModel.getCreationDate().toString());
        }
    }
}
