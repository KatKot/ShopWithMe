package com.kotwicka.shopwithme.shoppinglists.model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class ShoppingListViewModel {

    private final String name;
    private final DateTime creationDate;


    public ShoppingListViewModel(String name, DateTime creationDate) {
        this.name = name;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate.toLocalDate();
    }
}
