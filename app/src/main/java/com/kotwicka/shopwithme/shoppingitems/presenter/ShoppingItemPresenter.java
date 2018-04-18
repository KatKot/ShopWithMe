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

    private static final String TAG = ShoppingItemPresenter.class.getSimpleName();

    private final ShoppingItemContract.View view;
    private final ShoppingItemContract.Model model;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ShoppingItemPresenter(ShoppingItemContract.View view, ShoppingItemContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void loadShoppingItems(final long listId) {
        compositeDisposable.add(model.getShoppingItems(listId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ShoppingItemViewModel>>() {
                    @Override
                    public void accept(List<ShoppingItemViewModel> shoppingItemViewModels) throws Exception {
                        Log.d(TAG, String.format("Loaded %d shopping items.", shoppingItemViewModels.size()));
                        view.setShoppingItems(shoppingItemViewModels);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "Could not load shopping items.", throwable);
                    }
                }));

    }

    @Override
    public void saveShoppingItem(final long listId, final String shoppingItemName) {
        compositeDisposable.add(model.saveShoppingItem(listId, shoppingItemName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, String.format("Saved shopping item with name %s.", shoppingItemName));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, String.format("Could not save shopping item with name %s.", shoppingItemName), throwable);
                    }
                }));
    }

    @Override
    public void deleteShoppingListItem(final ShoppingItemViewModel shoppingItemViewModel, final long listId) {
        compositeDisposable.add(model.deleteShoppingListItem(shoppingItemViewModel, listId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, String.format("Deleted shopping item with name %s.", shoppingItemViewModel.getName()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, String.format("Could not delete shopping item with name %s.", shoppingItemViewModel.getName()));
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
