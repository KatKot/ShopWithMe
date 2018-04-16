package com.kotwicka.shopwithme.shoppinglists.contract;

public interface ShoppingListContract {

    interface View {
        void showEmptyListNameError();
        void showDuplicatedListNameError();
        void clearListNameError();
        void onNewShoppingListSaved();
    }

    interface Presenter {
        boolean validateShoppingListName(final String name);
        void saveShoppingList(final String name);
    }

    interface Model {

    }
}
