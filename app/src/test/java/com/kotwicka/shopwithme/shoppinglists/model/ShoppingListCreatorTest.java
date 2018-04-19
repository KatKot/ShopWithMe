package com.kotwicka.shopwithme.shoppinglists.model;

import com.kotwicka.shopwithme.persistence.entity.ShoppingList;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingListCreatorTest {

    @Test
    public void shouldCreateShoppingListForName() {
        // given
        final String name = "list";

        // when
        final ShoppingList shoppingList = ShoppingListCreator.fromName(name);

        // then
        assertThat(shoppingList.isArchived()).isFalse();
        assertThat(shoppingList.getCreationDate()).isNotNull();
        assertThat(shoppingList.getName()).isEqualTo(name);
    }

    @Test
    public void shouldCreateShoppingListForViewModel() {
        // given
        final ShoppingListViewModel shoppingListViewModel = new ShoppingListViewModel("name", DateTime.now(), 1L, false);

        // when
        final ShoppingList shoppingList = ShoppingListCreator.fromViewModel(shoppingListViewModel);

        // then
        assertThat(shoppingList.getName()).isEqualTo(shoppingListViewModel.getName());
        assertThat(shoppingList.getCreationDate()).isEqualTo(shoppingListViewModel.getCreationDateTime());
        assertThat(shoppingList.getId()).isEqualTo(shoppingListViewModel.getId());
        assertThat(shoppingList.isArchived()).isEqualTo(shoppingListViewModel.isArchived());
    }
}
