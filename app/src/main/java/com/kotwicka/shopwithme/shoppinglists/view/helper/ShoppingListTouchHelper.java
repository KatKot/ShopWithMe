package com.kotwicka.shopwithme.shoppinglists.view.helper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.kotwicka.shopwithme.shoppinglists.view.adapter.ShoppingListAdapter;
import android.view.View;

public class ShoppingListTouchHelper extends ItemTouchHelper.SimpleCallback {

    public interface OnSwipedListener {
        void onSwiped(int position);
    }

    private final OnSwipedListener listener;

    public ShoppingListTouchHelper(int dragDirs, int swipeDirs, final OnSwipedListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null) {
           final View foreground = ((ShoppingListAdapter.ShoppingListViewHolder) viewHolder).getForegroundView();
           getDefaultUIUtil().onSelected(foreground);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foreground = ((ShoppingListAdapter.ShoppingListViewHolder) viewHolder).getForegroundView();
        getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foreground = ((ShoppingListAdapter.ShoppingListViewHolder) viewHolder).getForegroundView();
        getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foreground = ((ShoppingListAdapter.ShoppingListViewHolder) viewHolder).getForegroundView();
        getDefaultUIUtil().clearView(foreground);
    }
}
