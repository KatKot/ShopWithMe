package com.kotwicka.shopwithme.presenter;

import com.kotwicka.shopwithme.shoppinglists.contract.ShoppingListContract;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListId;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListViewModel;
import com.kotwicka.shopwithme.shoppinglists.presenter.ShoppingListPresenter;
import com.kotwicka.shopwithme.test.util.ImmediateSchedulersRule;

import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.junit.ClassRule;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingListPresenterTest {

    @ClassRule
    public static ImmediateSchedulersRule immediateSchedulersRule = new ImmediateSchedulersRule();

    @Mock
    private ShoppingListContract.View view;

    @Mock
    private ShoppingListContract.Model model;

    @Mock
    private ShoppingListId shoppingListId;

    @InjectMocks
    private ShoppingListPresenter presenter;

    @Test
    public void shouldReturnFalseForEmptyName() {
        // given
        final String name = " ";

        // when
        final boolean isValid = presenter.isValidShoppingList(name);

        // then
        Mockito.verify(view).showEmptyListNameError();
        assertThat(isValid).isFalse();
    }

    @Test
    public void shouldReturnTrueForNotEmptyName() {
        // given
        final String name = "shoppingList";

        // when
        final boolean isValid = presenter.isValidShoppingList(name);

        // then
        Mockito.verify(view).clearListNameError();
        assertThat(isValid).isTrue();
    }

    @Test
    public void shouldFetchArchivedShoppingLists() {
        // given
        final ShoppingListViewModel list1 = new ShoppingListViewModel("list1", DateTime.now(), 1, true);
        final ShoppingListViewModel list2 = new ShoppingListViewModel("list2", DateTime.now(), 2, true);
        final ShoppingListViewModel list3 = new ShoppingListViewModel("list3", DateTime.now(), 3, true);
        final List<ShoppingListViewModel> lists = Lists.newArrayList(list1, list2, list3);
        final Flowable<List<ShoppingListViewModel>> data = Flowable.fromArray(lists);

        // when
        when(model.getArchiveShoppingLists()).thenReturn(data);
        presenter.fetchShoppingLists(false);

        // then
        Mockito.verify(view).setShoppingLists(lists);
        Mockito.verify(model).getArchiveShoppingLists();
    }

    @Test
    public void shouldFetchActiveShoppingLists() {
        // given
        final ShoppingListViewModel list1 = new ShoppingListViewModel("list1", DateTime.now(), 1, false);
        final ShoppingListViewModel list2 = new ShoppingListViewModel("list2", DateTime.now(), 2, false);
        final ShoppingListViewModel list3 = new ShoppingListViewModel("list3", DateTime.now(), 3, false);
        final List<ShoppingListViewModel> lists = Lists.newArrayList(list1, list2, list3);
        final Flowable<List<ShoppingListViewModel>> data = Flowable.fromArray(lists);

        // when
        when(model.getActiveShoppingLists()).thenReturn(data);
        presenter.fetchShoppingLists(true);

        // then
        Mockito.verify(view).setShoppingLists(lists);
        Mockito.verify(model).getActiveShoppingLists();
    }

    @Test
    public void shouldSaveValidShoppingList() {
        // given
        final String name = "shoppingList";
        final Long id = 1L;

        // when
        when(model.saveShoppingList(name)).thenReturn(Completable.complete());
        when(shoppingListId.getShoppingListId()).thenReturn(id);

        presenter.saveShoppingList(name);

        //then
        Mockito.verify(view).onNewShoppingListSaved(id, name);
        Mockito.verify(model).saveShoppingList(name);
    }


    @Test
    public void shouldDoNothingForInvalidShoppingList() {
        // given
        final String name = "";

        // when
        presenter.saveShoppingList(name);

        //then
        Mockito.verify(view, Mockito.never()).onNewShoppingListSaved(anyLong(), anyString());
        Mockito.verifyZeroInteractions(model);
        Mockito.verifyZeroInteractions(shoppingListId);
    }

    @Test
    public void shouldArchiveShoppingList() {
        // given
        final ShoppingListViewModel shoppingList = new ShoppingListViewModel("list", DateTime.now(), 1, false);

        // when
        when(model.archiveShoppingList(shoppingList)).thenReturn(Completable.complete());
        presenter.archiveShoppingList(shoppingList);

        // then
        Mockito.verify(model).archiveShoppingList(shoppingList);
    }

}
