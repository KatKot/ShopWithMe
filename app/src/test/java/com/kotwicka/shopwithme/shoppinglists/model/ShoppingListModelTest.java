package com.kotwicka.shopwithme.shoppinglists.model;

import com.kotwicka.shopwithme.persistence.entity.ShoppingList;
import com.kotwicka.shopwithme.persistence.repository.ShoppingListRepository;

import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingListModelTest {

    @Mock
    private ShoppingListRepository shoppingListRepository;

    @InjectMocks
    private ShoppingListModel shoppingListModel;

    @Test
    public void shouldInsertShoppingList() {
        // given
        final String name = "list";

        // when
        when(shoppingListRepository.insertShoppingList(any(ShoppingList.class))).thenReturn(Completable.complete());
        final Completable completable = shoppingListModel.saveShoppingList(name);
        completable.blockingAwait();

        // then
        final ArgumentCaptor<ShoppingList> shoppingListCaptor = ArgumentCaptor.forClass(ShoppingList.class);
        Mockito.verify(shoppingListRepository).insertShoppingList(shoppingListCaptor.capture());
        final ShoppingList actualInsertedList = shoppingListCaptor.getValue();
        assertThat(actualInsertedList.getCreationDate()).isNotNull();
        assertThat(actualInsertedList.isArchived()).isFalse();
        assertThat(actualInsertedList.getName()).isEqualTo(name);
    }

    @Test
    public void shouldArchiveShoppingList() {
        // given
        final ShoppingListViewModel shoppingListViewModel = new ShoppingListViewModel("list", DateTime.now(), 1L, false);
        final ShoppingList expectedShoppingList = new ShoppingList();
        expectedShoppingList.setArchived(true);
        expectedShoppingList.setCreationDate(shoppingListViewModel.getCreationDateTime());
        expectedShoppingList.setName(shoppingListViewModel.getName());
        expectedShoppingList.setId(shoppingListViewModel.getId());

        // when
        when(shoppingListRepository.updateShoppingList(any(ShoppingList.class))).thenReturn(Completable.complete());
        final Completable completable = shoppingListModel.archiveShoppingList(shoppingListViewModel);
        completable.blockingAwait();

        // then
        Mockito.verify(shoppingListRepository).updateShoppingList(expectedShoppingList);
    }

    @Test
    public void shouldGetActiveShoppingLists() {
        // given
        final ShoppingListViewModel shoppingListViewModel = new ShoppingListViewModel("list", DateTime.now(), 1L, false);
        final ShoppingListViewModel shoppingListViewModel2 = new ShoppingListViewModel("list2", DateTime.now(), 2L, false);
        final List<ShoppingListViewModel> expectedLists = Lists.newArrayList(shoppingListViewModel, shoppingListViewModel2);
        final ShoppingList shoppingList = ShoppingListCreator.fromViewModel(shoppingListViewModel);
        final ShoppingList shoppingList2 = ShoppingListCreator.fromViewModel(shoppingListViewModel2);
        final List<ShoppingList> shoppingLists = Lists.newArrayList(shoppingList, shoppingList2);
        final Flowable<List<ShoppingList>> shoppingListsStream = Flowable.fromArray(shoppingLists);

        // when
        when(shoppingListRepository.getActiveShoppingLists()).thenReturn(shoppingListsStream);
        final Flowable<List<ShoppingListViewModel>> actualStream = shoppingListModel.getActiveShoppingLists();

        // then
        final List<ShoppingListViewModel> actualLists = actualStream.blockingFirst();
        assertThat(actualLists).isEqualTo(expectedLists);
    }

    @Test
    public void shouldGetArchivedShoppingLists() {
        // given
        final ShoppingListViewModel shoppingListViewModel = new ShoppingListViewModel("list", DateTime.now(), 1L, true);
        final ShoppingListViewModel shoppingListViewModel2 = new ShoppingListViewModel("list2", DateTime.now(), 2L, true);
        final List<ShoppingListViewModel> expectedLists = Lists.newArrayList(shoppingListViewModel, shoppingListViewModel2);
        final ShoppingList shoppingList = ShoppingListCreator.fromViewModel(shoppingListViewModel);
        final ShoppingList shoppingList2 = ShoppingListCreator.fromViewModel(shoppingListViewModel2);
        final List<ShoppingList> shoppingLists = Lists.newArrayList(shoppingList, shoppingList2);
        final Flowable<List<ShoppingList>> shoppingListsStream = Flowable.fromArray(shoppingLists);

        // when
        when(shoppingListRepository.getArchivedShoppingLists()).thenReturn(shoppingListsStream);
        final Flowable<List<ShoppingListViewModel>> actualStream = shoppingListModel.getArchiveShoppingLists();

        // then
        final List<ShoppingListViewModel> actualLists = actualStream.blockingFirst();
        assertThat(actualLists).isEqualTo(expectedLists);
    }
}
