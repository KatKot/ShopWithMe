package com.kotwicka.shopwithme.shoppingitems.presenter;

import com.kotwicka.shopwithme.shoppingitems.contract.ShoppingItemContract;
import com.kotwicka.shopwithme.shoppingitems.model.ShoppingItemViewModel;
import com.kotwicka.shopwithme.test.util.ImmediateSchedulersRule;

import org.assertj.core.util.Lists;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingItemPresenterTest {

    @ClassRule
    public static ImmediateSchedulersRule rule = new ImmediateSchedulersRule();

    @Mock
    private ShoppingItemContract.View view;

    @Mock
    private ShoppingItemContract.Model model;

    @InjectMocks
    private ShoppingItemPresenter presenter;

    @Test
    public void shouldLoadShoppingItems() {
        // given
        final long listId = 1L;
        final ShoppingItemViewModel shoppingItemViewModel = new ShoppingItemViewModel("item1", 1);
        final ShoppingItemViewModel shoppingItemViewModel2 = new ShoppingItemViewModel("item2", 2);
        final List<ShoppingItemViewModel> items = Lists.newArrayList(shoppingItemViewModel, shoppingItemViewModel2);
        final Flowable<List<ShoppingItemViewModel>> data = Flowable.fromArray(items);

        // when
        when(model.getShoppingItems(listId)).thenReturn(data);

        presenter.loadShoppingItems(listId);

        // then
        verify(model).getShoppingItems(listId);
        verify(view).setShoppingItems(items);
    }

    @Test
    public void shouldSaveShoppingItem() {
        // given
        final long listId = 1L;
        final String itemName = "item";

        // when
        when(model.saveShoppingItem(listId, itemName)).thenReturn(Completable.complete());
        presenter.saveShoppingItem(listId, itemName);

        // then
        verify(model).saveShoppingItem(listId, itemName);
    }

    @Test
    public void shouldDeleteShoppingItem() {
        // given
        final ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("item", 1L);
        final long shoppingListId = 2L;

        // when
        when(model.deleteShoppingListItem(shoppingItem, shoppingListId)).thenReturn(Completable.complete());
        presenter.deleteShoppingListItem(shoppingItem, shoppingListId);

        // then
        verify(model).deleteShoppingListItem(shoppingItem, shoppingListId);
    }
}
