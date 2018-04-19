package com.kotwicka.shopwithme.persistence.repository;

import com.kotwicka.shopwithme.persistence.dao.ShoppingListItemDao;
import com.kotwicka.shopwithme.persistence.db.ShoppingDatabase;
import com.kotwicka.shopwithme.persistence.entity.ShoppingListItem;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingItemRepositoryTest {

    @Mock
    private ShoppingDatabase shoppingDatabase;

    @Mock
    private ShoppingListItemDao shoppingListItemDao;

    @InjectMocks
    private ShoppingItemRepository shoppingItemRepository;

    @Before
    public void setUp() {
        when(shoppingDatabase.shoppingListItemDao()).thenReturn(shoppingListItemDao);
    }

    @Test
    public void shouldInsertItemToDatabase() {
        // given
        final ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setId(1L);
        shoppingListItem.setShoppingListId(2l);
        shoppingListItem.setName("item");

        // when
        final Completable result = shoppingItemRepository.insertShoppingListItem(shoppingListItem);
        result.blockingAwait();

        // then
        Mockito.verify(shoppingListItemDao).insertShoppingListItem(shoppingListItem);
    }

    @Test
    public void shouldDeleteItemFromDatabase() {
        // given
        final ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setId(1L);
        shoppingListItem.setShoppingListId(2l);
        shoppingListItem.setName("item");

        // when
        final Completable result = shoppingItemRepository.deleteShoppingListItem(shoppingListItem);
        result.blockingAwait();

        // then
        Mockito.verify(shoppingListItemDao).deleteShoppingListItem(shoppingListItem);
    }

    @Test
    public void shouldGetItemsFromDatabase() {
        // given
        final long listId = 1L;
        final List<ShoppingListItem> items = Lists.<ShoppingListItem>newArrayList(new ShoppingListItem());
        final Flowable<List<ShoppingListItem>> dataStream = Flowable.fromArray(items);

        // when
        when(shoppingListItemDao.getItemsForShoppingList(listId)).thenReturn(dataStream);
        final Flowable<List<ShoppingListItem>> result = shoppingItemRepository.getShoppingListItems(listId);

        // then
        assertThat(result).isEqualTo(dataStream);
    }
}
