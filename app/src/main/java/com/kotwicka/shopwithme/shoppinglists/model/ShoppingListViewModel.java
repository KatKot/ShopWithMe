package com.kotwicka.shopwithme.shoppinglists.model;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class ShoppingListViewModel {

    private final String name;
    private final DateTime creationDate;
    private final long id;


    public ShoppingListViewModel(String name, DateTime creationDate, long id) {
        this.name = name;
        this.creationDate = creationDate;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreationDate() {
        return creationDate.toLocalDate();
    }

    public long getId() {
        return id;
    }
}
