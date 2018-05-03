package com.kotwicka.shopwithme.shoppinglists.presenter;

import android.util.Log;

import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListId;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListViewModel;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShoppingListPresenter implements ShoppingListContract.Presenter {

    private static final String TAG = ShoppingListPresenter.class.getSimpleName();

    private ShoppingListContract.View view;
    private final ShoppingListContract.Model model;
    private final ShoppingListId shoppingListId;

    private CompositeDisposable flowableDisposable = new CompositeDisposable();
    private CompositeDisposable completableDisposable = new CompositeDisposable();

    public ShoppingListPresenter(final ShoppingListContract.View view, final ShoppingListContract.Model model, final ShoppingListId shoppingListId) {
        this.view = view;
        this.model = model;
        this.shoppingListId = shoppingListId;
    }

    @Override
    public boolean isValidShoppingList(final String name) {
        if (StringUtils.isBlank(name)) {
            view.showEmptyListNameError();
            return false;
        }
        view.clearListNameError();
        return true;
    }

    @Override
    public void fetchShoppingLists(final boolean shouldFetchActiveLists) {
        flowableDisposable.dispose();
        flowableDisposable = new CompositeDisposable();
        if (shouldFetchActiveLists) {
            fetchActiveShoppingLists();
        } else {
            fetchArchivedShoppingLists();
        }
    }

    private void fetchArchivedShoppingLists() {
        flowableDisposable.add(model.getArchiveShoppingLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ShoppingListViewModel>>() {
                    @Override
                    public void accept(List<ShoppingListViewModel> shoppingListViewModels) throws Exception {
                        Log.d(TAG, String.format("Loaded %d archived shopping lists.", shoppingListViewModels.size()));
                        view.setShoppingLists(shoppingListViewModels);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "Could not load archived shopping lists.", throwable);
                    }
                }));
    }

    private void fetchActiveShoppingLists() {
        flowableDisposable.add(model.getActiveShoppingLists()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ShoppingListViewModel>>() {
                    @Override
                    public void accept(List<ShoppingListViewModel> shoppingListViewModels) throws Exception {
                        Log.d(TAG, String.format("Loaded %d active shopping lists.", shoppingListViewModels.size()));
                        view.setShoppingLists(shoppingListViewModels);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "Could not load active shopping lists.", throwable);
                    }
                }));
    }

    @Override
    public void saveShoppingList(final String name) {
        if (isValidShoppingList(name)) {
            completableDisposable.add(model.saveShoppingList(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            Log.d(TAG, String.format("Saved shopping list with name %s.", name));
                            view.onNewShoppingListSaved(shoppingListId.getShoppingListId(), name);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e(TAG, String.format("Could not save shopping list with name %s.", name), throwable);
                        }
                    }));
        }
    }

    @Override
    public void archiveShoppingList(final ShoppingListViewModel shoppingListViewModel) {
        completableDisposable.add(model.archiveShoppingList(shoppingListViewModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d(TAG, String.format("Archived shopping list with name %s.", shoppingListViewModel.getName()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, String.format("Could not archive shopping list with name %s.", shoppingListViewModel.getName()), throwable);
                    }
                }));
    }

    @Override
    public void onDetachView() {
        if (flowableDisposable != null) {
            flowableDisposable.dispose();
        }
        if (completableDisposable != null) {
            completableDisposable.dispose();
        }
        this.view = null;
    }
}
