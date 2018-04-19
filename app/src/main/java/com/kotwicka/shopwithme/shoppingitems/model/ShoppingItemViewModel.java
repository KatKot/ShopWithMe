package com.kotwicka.shopwithme.shoppingitems.model;

public class ShoppingItemViewModel {

    private final String name;
    private final long id;

    public ShoppingItemViewModel(String name, long id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingItemViewModel that = (ShoppingItemViewModel) o;

        if (id != that.id) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }
}
