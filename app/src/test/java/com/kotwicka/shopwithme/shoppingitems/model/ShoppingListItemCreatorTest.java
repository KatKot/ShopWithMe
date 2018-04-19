package com.kotwicka.shopwithme.shoppingitems.model;

import com.kotwicka.shopwithme.persistence.entity.ShoppingListItem;

import org.junit.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListItemCreatorTest {

    @Test
    public void shouldCreateListItemForGivenData() {
        // given
        final String name = "item";
        final long shoppingListId = 7L;

        // when
        final ShoppingListItem item = ShoppingListItemCreator.fromNameAndListId(name, shoppingListId);

        // then
        assertThat(item.getShoppingListId()).isEqualTo(shoppingListId);
        assertThat(item.getName()).isEqualTo(name);
    }

    @Test
    public void shouldCreateListItemForViewModel() {
        // given
        final ShoppingItemViewModel itemViewModel = new ShoppingItemViewModel("name", 1L);
        final long shoppingListId = 5L;

        // when
        final ShoppingListItem item = ShoppingListItemCreator.fromViewModel(itemViewModel, shoppingListId);

        //then
        assertThat(item.getName()).isEqualTo(itemViewModel.getName());
        assertThat(item.getId()).isEqualTo(itemViewModel.getId());
        assertThat(item.getShoppingListId()).isEqualTo(shoppingListId);

    }
}
