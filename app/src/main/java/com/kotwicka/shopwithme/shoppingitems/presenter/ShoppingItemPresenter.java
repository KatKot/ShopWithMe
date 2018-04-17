package com.kotwicka.shopwithme.shoppingitems.presenter;

import android.util.Log;

import com.kotwicka.shopwithme.shoppingitems.contract.ShoppingItemContract;
import com.kotwicka.shopwithme.shoppingitems.model.ShoppingItemViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShoppingItemPresenter implements ShoppingItemContract.Presenter {

    private final ShoppingItemContract.View view;
    private final ShoppingItemContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ShoppingItemPresenter(ShoppingItemContract.View view, ShoppingItemContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void saveShoppingItem(final long listId, final String shoppingItemName) {
        Log.d("ShoppingItemsPresenter", "Saving item with name : " + shoppingItemName + " listID: " + listId);
        compositeDisposable.add(model.saveShoppingItem(listId, shoppingItemName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ShoppingItemPresenter", "Saved shopping item : " + shoppingItemName);
                    }
                }));
    }

    @Override
    public void loadShoppingItems(final long listId) {
        Log.d("ShoppingItemsPresenter", "Loading shopping items for listID: " + listId);
        compositeDisposable.add(model.getShoppingItems(listId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ShoppingItemViewModel>>() {
                    @Override
                    public void accept(List<ShoppingItemViewModel> shoppingItemViewModels) throws Exception {
                        Log.d("ShoppingItemsPresenter", "Loaded shopping items with size: " + shoppingItemViewModels.size());
                        view.setShoppingItems(shoppingItemViewModels);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ShoppingItemPreenter", "Could not fetch shopping items", throwable);
                    }
                }));

    }

    @Override
    public void onDetachView() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
