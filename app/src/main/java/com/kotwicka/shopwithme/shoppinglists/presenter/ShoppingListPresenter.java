package com.kotwicka.shopwithme.shoppinglists.presenter;

import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;

import org.apache.commons.lang3.StringUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShoppingListPresenter implements ShoppingListContract.Presenter {

    private final ShoppingListContract.View view;
    private final ShoppingListContract.Model model;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    public ShoppingListPresenter(final ShoppingListContract.View view, final ShoppingListContract.Model model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public boolean isValidShoppingList(final String name) {
        if (StringUtils.isBlank(name)) {
            view.showEmptyListNameError();
            return false;
        }
        return true;
    }

    @Override
    public void saveShoppingList(final String name) {
        if (isValidShoppingList(name)) {
            compositeDisposable.add(model.saveShoppingList(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            view.onNewShoppingListSaved(name);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.showSaveListErrorMessage();
                        }
                    }));
        }
    }

    @Override
    public void onDetachView() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
