package com.kotwicka.shopwithme.shoppingitems.model;

public class ShoppingItemViewModel {

    private final String name;
    private final long id;

    public ShoppingItemViewModel(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
