package com.kotwicka.shopwithme.shoppingitems.adapter;

public enum ViewHolderType {

    ITEM(1),
    STUB(2);

    private int type;

    ViewHolderType(final int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
