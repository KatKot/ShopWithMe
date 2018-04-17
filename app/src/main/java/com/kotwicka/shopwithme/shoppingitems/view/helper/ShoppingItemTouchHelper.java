package com.kotwicka.shopwithme.shoppingitems.view.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.kotwicka.shopwithme.shoppingitems.adapter.ShoppingItemAdapter;

public class ShoppingItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    public interface OnSwipedListener {
        void onSwiped(int position);
    }

    private final OnSwipedListener listener;

    public ShoppingItemTouchHelper(int dragDirs, int swipeDirs, final OnSwipedListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (isShoppingItem(viewHolder)) {
            return true;
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (isShoppingItem(viewHolder)) {
            listener.onSwiped(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null && isShoppingItem(viewHolder)) {
            final View foreground = ((ShoppingItemAdapter.ItemViewHolder) viewHolder).getForegroundView();
            getDefaultUIUtil().onSelected(foreground);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (isShoppingItem(viewHolder)) {
            final View foreground = ((ShoppingItemAdapter.ItemViewHolder) viewHolder).getForegroundView();
            getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (isShoppingItem(viewHolder)) {
            final View foreground = ((ShoppingItemAdapter.ItemViewHolder) viewHolder).getForegroundView();
            getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (isShoppingItem(viewHolder)) {
            final View foreground = ((ShoppingItemAdapter.ItemViewHolder) viewHolder).getForegroundView();
            getDefaultUIUtil().clearView(foreground);
        }
    }

    private boolean isShoppingItem(RecyclerView.ViewHolder viewHolder) {
        return viewHolder instanceof ShoppingItemAdapter.ItemViewHolder;
    }

}
