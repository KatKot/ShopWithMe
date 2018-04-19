package com.kotwicka.shopwithme.shoppinglists.view.adapter;


import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kotwicka.shopwithme.BuildConfig;
import com.kotwicka.shopwithme.shoppinglists.model.ShoppingListViewModel;

import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import android.view.View;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class ShoppingListAdapterTest {

    private ShoppingListAdapter.OnShoppingListClickedListener onShoppingClickedListener;
    private Context context;

    @Before
    public void setup() {
        context = RuntimeEnvironment.application;
        onShoppingClickedListener = Mockito.mock(ShoppingListAdapter.OnShoppingListClickedListener.class);
    }

    @Test
    public void shouldAddNewListToAdapterData() {
        // given
        ShoppingListViewModel shoppingList = new ShoppingListViewModel("list1", DateTime.now(), 1L, false);
        ShoppingListAdapter adapter = spy(new ShoppingListAdapter(onShoppingClickedListener));

        // when
        adapter.add(shoppingList);

        // then
        Mockito.verify(adapter).notifyItemInserted(0);
        assertThat(adapter.getItemCount()).isEqualTo(1);
    }

    @Test
    public void shouldClearOldElementsAndAddAllNewElementsToAdapterData() {
        // given
        ShoppingListViewModel shoppingList = new ShoppingListViewModel("list1", DateTime.now(), 1L, false);
        ShoppingListViewModel shoppingList2 = new ShoppingListViewModel("list2", DateTime.now(), 2L, false);
        ShoppingListViewModel shoppingList3 = new ShoppingListViewModel("list3", DateTime.now(), 3L, false);
        ShoppingListAdapter adapter = spy(new ShoppingListAdapter(onShoppingClickedListener));

        // when
        adapter.add(shoppingList);
        adapter.setLists(Lists.newArrayList(shoppingList2, shoppingList3));

        // then
        Mockito.verify(adapter).notifyDataSetChanged();
        Mockito.verify(adapter, times(3)).notifyItemInserted(anyInt());
        assertThat(adapter.getItemCount()).isEqualTo(2);
    }

    @Test
    public void shouldGetCorrectItem() {
        // given
        ShoppingListViewModel shoppingList = new ShoppingListViewModel("list1", DateTime.now(), 1L, false);
        ShoppingListViewModel shoppingList2 = new ShoppingListViewModel("list2", DateTime.now(), 2L, false);
        ShoppingListViewModel shoppingList3 = new ShoppingListViewModel("list3", DateTime.now(), 3L, false);

        ShoppingListAdapter adapter = spy(new ShoppingListAdapter(onShoppingClickedListener));

        // when
        adapter.setLists(Lists.newArrayList(shoppingList, shoppingList2, shoppingList3));
        final ShoppingListViewModel actualShoppingList = adapter.get(1);

        // then
        assertThat(actualShoppingList).isEqualTo(shoppingList2);
    }

    @Test
    public void shouldCreateElementView() {
        // given
        ShoppingListViewModel shoppingList = new ShoppingListViewModel("list1", DateTime.now(), 1L, false);
        ShoppingListViewModel shoppingList2 = new ShoppingListViewModel("list2", DateTime.now(), 2L, false);
        ShoppingListViewModel shoppingList3 = new ShoppingListViewModel("list3", DateTime.now(), 3L, false);

        ShoppingListAdapter adapter = spy(new ShoppingListAdapter(onShoppingClickedListener));


        RecyclerView rvParent = new RecyclerView(context);
        rvParent.setLayoutManager(new LinearLayoutManager(context));


        // when
        adapter.setLists(Lists.newArrayList(shoppingList, shoppingList2, shoppingList3));
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(rvParent, 0);

        // then
        assertThat(viewHolder).isExactlyInstanceOf(ShoppingListAdapter.ShoppingListViewHolder.class);
        ShoppingListAdapter.ShoppingListViewHolder shoppingListView = (ShoppingListAdapter.ShoppingListViewHolder) viewHolder;
        assertThat(shoppingListView.shoppingListName.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(shoppingListView.foregroundView.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(shoppingListView.shoppingListCreationDate.getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test
    public void shouldBindElementView() {
        // given
        ShoppingListViewModel shoppingList = new ShoppingListViewModel("list1", DateTime.now(), 1L, false);
        ShoppingListViewModel shoppingList2 = new ShoppingListViewModel("list2", DateTime.now(), 2L, false);
        ShoppingListViewModel shoppingList3 = new ShoppingListViewModel("list3", DateTime.now(), 3L, false);

        ShoppingListAdapter adapter = spy(new ShoppingListAdapter(onShoppingClickedListener));


        RecyclerView rvParent = new RecyclerView(context);
        rvParent.setLayoutManager(new LinearLayoutManager(context));


        // when
        adapter.setLists(Lists.newArrayList(shoppingList, shoppingList2, shoppingList3));
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(rvParent, 0);
        adapter.onBindViewHolder(viewHolder, 0);

        // then
        assertThat(viewHolder).isExactlyInstanceOf(ShoppingListAdapter.ShoppingListViewHolder.class);
        ShoppingListAdapter.ShoppingListViewHolder shoppingListView = (ShoppingListAdapter.ShoppingListViewHolder) viewHolder;
        assertThat(shoppingListView.shoppingListName.getText().toString()).isEqualTo(shoppingList.getName());
        assertThat(shoppingListView.shoppingListCreationDate.getText().toString()).isEqualTo(shoppingList.getCreationDate().toString());
    }

}
