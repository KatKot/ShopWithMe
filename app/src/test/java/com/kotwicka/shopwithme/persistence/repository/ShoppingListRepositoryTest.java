package com.kotwicka.shopwithme.persistence.repository;

import com.kotwicka.shopwithme.persistence.dao.ShoppingListDao;
import com.kotwicka.shopwithme.persistence.db.ShoppingDatabase;
import com.kotwicka.shopwithme.persistence.entity.ShoppingList;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListId;

import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
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
public class ShoppingListRepositoryTest {

    @Mock
    private ShoppingDatabase shoppingDatabase;

    @Mock
    private ShoppingListDao shoppingListDao;

    @Mock
    private ShoppingListId shoppingListId;

    @InjectMocks
    private ShoppingListRepository shoppingListRepository;

    @Before
    public void setUp() {
        when(shoppingDatabase.shoppingListDao()).thenReturn(shoppingListDao);
    }

    @Test
    public void shouldInsertListToDatabase() {
        // given
        final long listId = 6l;
        final ShoppingList shoppingList = new ShoppingList();
        shoppingList.setArchived(false);
        shoppingList.setName("list");
        shoppingList.setCreationDate(DateTime.now());

        // when
        when(shoppingListDao.insertShoppingList(shoppingList)).thenReturn(listId);
        final Completable result = shoppingListRepository.insertShoppingList(shoppingList);
        result.blockingAwait();

        // then
        Mockito.verify(shoppingListDao).insertShoppingList(shoppingList);
        Mockito.verify(shoppingListId).setShoppingListId(listId);
    }

    @Test
    public void shouldUpdateListInDatabase() {
        // given
        final ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(1L);
        shoppingList.setArchived(true);
        shoppingList.setName("list");
        shoppingList.setCreationDate(DateTime.now());

        // when
        final Completable result = shoppingListRepository.updateShoppingList(shoppingList);
        result.blockingAwait();

        // then
        Mockito.verify(shoppingListDao).updateShoppingList(shoppingList);
    }

    @Test
    public void shouldGetActiveListsFromDatabase() {
        // given
        final List<ShoppingList> items = Lists.<ShoppingList>newArrayList(new ShoppingList());
        final Flowable<List<ShoppingList>> dataStream = Flowable.fromArray(items);

        // when
        when(shoppingListDao.getActiveShoppingLists()).thenReturn(dataStream);
        final Flowable<List<ShoppingList>> result = shoppingListRepository.getActiveShoppingLists();

        // then
        assertThat(result).isEqualTo(dataStream);
    }

    @Test
    public void shouldGetArchivedListsFromDatabase() {
        // given
        final List<ShoppingList> items = Lists.<ShoppingList>newArrayList(new ShoppingList());
        final Flowable<List<ShoppingList>> dataStream = Flowable.fromArray(items);

        // when
        when(shoppingListDao.getArchivedShoppingLists()).thenReturn(dataStream);
        final Flowable<List<ShoppingList>> result = shoppingListRepository.getArchivedShoppingLists();

        // then
        assertThat(result).isEqualTo(dataStream);
    }
}
