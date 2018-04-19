package com.kotwicka.shopwithme.shoppingitems.model;

import com.kotwicka.shopwithme.persistence.entity.ShoppingListItem;
import com.kotwicka.shopwithme.persistence.repository.ShoppingItemRepository;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingItemModelTest {

    @Mock
    private ShoppingItemRepository shoppingItemRepository;

    @InjectMocks
    private ShoppingItemModel shoppingItemModel;

    @Test
    public void shouldGetShoppingItemsFromRepository() {
        // given
        final long listId = 1L;
        final ShoppingItemViewModel shoppingItemViewModel = new ShoppingItemViewModel("item2", 2);
        final ShoppingListItem shoppingItem = ShoppingListItemCreator.fromViewModel(shoppingItemViewModel, listId);
        final ShoppingItemViewModel shoppingItemViewModel2 = new ShoppingItemViewModel("item2", 3);
        final ShoppingListItem shoppingItem2 = ShoppingListItemCreator.fromViewModel(shoppingItemViewModel2, listId);

        final List<ShoppingItemViewModel> items = Lists.newArrayList(shoppingItemViewModel, shoppingItemViewModel2);

        final List<ShoppingListItem> dbItems = Lists.newArrayList(shoppingItem, shoppingItem2);
        final Flowable<List<ShoppingListItem>> dbData = Flowable.fromArray(dbItems);


        // when
        when(shoppingItemRepository.getShoppingListItems(listId)).thenReturn(dbData);
        final Flowable<List<ShoppingItemViewModel>> dataStream = shoppingItemModel.getShoppingItems(listId);

        // then
        final List<ShoppingItemViewModel> actualData = dataStream.blockingFirst();

        verify(shoppingItemRepository).getShoppingListItems(listId);
        assertThat(actualData).isEqualTo(items);
    }

    @Test
    public void shouldInsertShoppingItemToRepository() {
        // given
        final long id = 1L;
        final String name = "item";
        final ShoppingListItem item = new ShoppingListItem();
        item.setShoppingListId(id);
        item.setName(name);

        // when
        when(shoppingItemRepository.insertShoppingListItem(item)).thenReturn(Completable.complete());
        shoppingItemModel.saveShoppingItem(id, name);

        // then
        verify(shoppingItemRepository).insertShoppingListItem(item);
    }

    @Test
    public void shouldDeleteShoppingItemFromRepository() {
        // given
        final ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("item" , 1L);
        final long id = 2L;
        final ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setName(shoppingItem.getName());
        shoppingListItem.setShoppingListId(id);
        shoppingListItem.setId(shoppingItem.getId());

        // when
        when(shoppingItemRepository.deleteShoppingListItem(shoppingListItem)).thenReturn(Completable.complete());
        shoppingItemModel.deleteShoppingListItem(shoppingItem, id);

        // then
        verify(shoppingItemRepository).deleteShoppingListItem(shoppingListItem);
    }
}
