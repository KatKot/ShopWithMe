package com.kotwicka.shopwithme.shoppingitems.view.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;

import com.kotwicka.shopwithme.BuildConfig;
import com.kotwicka.shopwithme.shoppingitems.model.ShoppingItemViewModel;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class ShoppingItemAdapterTest {

    private ShoppingItemAdapter.OnAcceptItemButtonClickedListener onAcceptItemButtonClickedListener;
    private Context context;

    @Before
    public void setup() {
        context = RuntimeEnvironment.application;
        onAcceptItemButtonClickedListener = Mockito.mock(ShoppingItemAdapter.OnAcceptItemButtonClickedListener.class);
    }

    @Test
    public void shouldAddNewItemToAdapterData() {
        // given
        ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("item", 1L);
        ShoppingItemAdapter adapter = spy(new ShoppingItemAdapter(onAcceptItemButtonClickedListener));

        // when
        adapter.add(shoppingItem);

        // then
        Mockito.verify(adapter).notifyItemInserted(0);
        assertThat(adapter.getItemCount()).isEqualTo(1);
    }

    @Test
    public void shouldClearOldItemsAndAddAllNewItemsToAdapterDataIncludingStubItem() {
        // given
        ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("list1", 1L);
        ShoppingItemViewModel shoppingItem2 = new ShoppingItemViewModel("list2", 2L);
        ShoppingItemViewModel shoppingItem3 = new ShoppingItemViewModel("list3", 3L);
        ShoppingItemAdapter adapter = spy(new ShoppingItemAdapter(onAcceptItemButtonClickedListener));

        // when
        adapter.add(shoppingItem);
        adapter.setShoppingItems(Lists.newArrayList(shoppingItem2, shoppingItem3), false);

        // then
        Mockito.verify(adapter).notifyDataSetChanged();
        Mockito.verify(adapter, times(4)).notifyItemInserted(anyInt());
        assertThat(adapter.getItemCount()).isEqualTo(3);
    }

    @Test
    public void shouldClearOldItemsAndAddAllNewItemsToAdapterData() {
        // given
        ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("list1", 1L);
        ShoppingItemViewModel shoppingItem2 = new ShoppingItemViewModel("list2", 2L);
        ShoppingItemViewModel shoppingItem3 = new ShoppingItemViewModel("list3", 3L);
        ShoppingItemAdapter adapter = spy(new ShoppingItemAdapter(onAcceptItemButtonClickedListener));

        // when
        adapter.add(shoppingItem);
        adapter.setShoppingItems(Lists.newArrayList(shoppingItem2, shoppingItem3), true);

        // then
        Mockito.verify(adapter).notifyDataSetChanged();
        Mockito.verify(adapter, times(3)).notifyItemInserted(anyInt());
        assertThat(adapter.getItemCount()).isEqualTo(2);
    }

    @Test
    public void shouldGetCorrectItem() {
        // given
        ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("list1", 1L);
        ShoppingItemViewModel shoppingItem2 = new ShoppingItemViewModel("list2", 2L);
        ShoppingItemViewModel shoppingItem3 = new ShoppingItemViewModel("list3", 3L);
        ShoppingItemAdapter adapter = spy(new ShoppingItemAdapter(onAcceptItemButtonClickedListener));

        // when
        adapter.setShoppingItems(Lists.newArrayList(shoppingItem, shoppingItem2, shoppingItem3), false);
        final ShoppingItemViewModel actualShoppingItems = adapter.get(1);

        // then
        assertThat(actualShoppingItems).isEqualTo(shoppingItem2);
    }

    @Test
    public void shouldGetViewTypeForStubItem() {
        // given
        ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("list1", 1L);
        ShoppingItemViewModel shoppingItem2 = new ShoppingItemViewModel("list2", 2L);
        ShoppingItemViewModel shoppingItem3 = new ShoppingItemViewModel("list3", 3L);
        ShoppingItemAdapter adapter = spy(new ShoppingItemAdapter(onAcceptItemButtonClickedListener));

        // when
        adapter.setShoppingItems(Lists.newArrayList(shoppingItem, shoppingItem2, shoppingItem3), false);
        final int viewType = adapter.getItemViewType(3);

        // then
        assertThat(viewType).isEqualTo(ViewHolderType.STUB.getType());
    }

    @Test
    public void shouldGetViewTypeForShoppingItem() {
        // given
        ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("list1", 1L);
        ShoppingItemViewModel shoppingItem2 = new ShoppingItemViewModel("list2", 2L);
        ShoppingItemViewModel shoppingItem3 = new ShoppingItemViewModel("list3", 3L);
        ShoppingItemAdapter adapter = spy(new ShoppingItemAdapter(onAcceptItemButtonClickedListener));

        // when
        adapter.setShoppingItems(Lists.newArrayList(shoppingItem, shoppingItem2, shoppingItem3), false);
        final int viewType = adapter.getItemViewType(2);

        // then
        assertThat(viewType).isEqualTo(ViewHolderType.ITEM.getType());
    }

    @Test
    public void shouldCreateViewForStubView() {
        // given
        ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("list1", 1L);
        ShoppingItemViewModel shoppingItem2 = new ShoppingItemViewModel("list2", 2L);
        ShoppingItemViewModel shoppingItem3 = new ShoppingItemViewModel("list3", 3L);
        ShoppingItemAdapter adapter = spy(new ShoppingItemAdapter(onAcceptItemButtonClickedListener));

        RecyclerView rvParent = new RecyclerView(context);
        rvParent.setLayoutManager(new LinearLayoutManager(context));

        // when
        adapter.setShoppingItems(Lists.newArrayList(shoppingItem, shoppingItem2, shoppingItem3), false);
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(rvParent, ViewHolderType.STUB.getType());

        // then
        assertThat(viewHolder).isExactlyInstanceOf(ShoppingItemAdapter.StubItemViewHolder.class);
        ShoppingItemAdapter.StubItemViewHolder shoppingItemView = (ShoppingItemAdapter.StubItemViewHolder) viewHolder;
        assertThat(shoppingItemView.button.getVisibility()).isEqualTo(View.INVISIBLE);
        assertThat(shoppingItemView.listItemStubEditText.getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test
    public void shouldCreateViewForItemView() {
        // given
        ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("list1", 1L);
        ShoppingItemViewModel shoppingItem2 = new ShoppingItemViewModel("list2", 2L);
        ShoppingItemViewModel shoppingItem3 = new ShoppingItemViewModel("list3", 3L);
        ShoppingItemAdapter adapter = spy(new ShoppingItemAdapter(onAcceptItemButtonClickedListener));

        RecyclerView rvParent = new RecyclerView(context);
        rvParent.setLayoutManager(new LinearLayoutManager(context));

        // when
        adapter.setShoppingItems(Lists.newArrayList(shoppingItem, shoppingItem2, shoppingItem3), false);
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(rvParent, ViewHolderType.ITEM.getType());

        // then
        assertThat(viewHolder).isExactlyInstanceOf(ShoppingItemAdapter.ItemViewHolder.class);
        ShoppingItemAdapter.ItemViewHolder shoppingItemView = (ShoppingItemAdapter.ItemViewHolder) viewHolder;
        assertThat(shoppingItemView.foregroundView.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(shoppingItemView.listItemNameTextView.getVisibility()).isEqualTo(View.VISIBLE);
    }

    @Test
    public void shouldBindViewForStubView() {
        // given
        ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("list1", 1L);
        ShoppingItemViewModel shoppingItem2 = new ShoppingItemViewModel("list2", 2L);
        ShoppingItemViewModel shoppingItem3 = new ShoppingItemViewModel("list3", 3L);
        ShoppingItemAdapter adapter = spy(new ShoppingItemAdapter(onAcceptItemButtonClickedListener));

        RecyclerView rvParent = new RecyclerView(context);
        rvParent.setLayoutManager(new LinearLayoutManager(context));

        // when
        adapter.setShoppingItems(Lists.newArrayList(shoppingItem, shoppingItem2, shoppingItem3), false);
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(rvParent, ViewHolderType.STUB.getType());
        adapter.onBindViewHolder(viewHolder, 3);

        // then
        assertThat(viewHolder).isInstanceOf(ShoppingItemAdapter.StubItemViewHolder.class);
        ShoppingItemAdapter.StubItemViewHolder shoppingItemView = (ShoppingItemAdapter.StubItemViewHolder) viewHolder;
        assertThat(shoppingItemView.button.hasOnClickListeners()).isTrue();
    }

    @Test
    public void shouldBindViewForItemView() {
        // given
        ShoppingItemViewModel shoppingItem = new ShoppingItemViewModel("list1", 1L);
        ShoppingItemViewModel shoppingItem2 = new ShoppingItemViewModel("list2", 2L);
        ShoppingItemViewModel shoppingItem3 = new ShoppingItemViewModel("list3", 3L);
        ShoppingItemAdapter adapter = spy(new ShoppingItemAdapter(onAcceptItemButtonClickedListener));

        RecyclerView rvParent = new RecyclerView(context);
        rvParent.setLayoutManager(new LinearLayoutManager(context));

        // when
        adapter.setShoppingItems(Lists.newArrayList(shoppingItem, shoppingItem2, shoppingItem3), false);
        RecyclerView.ViewHolder viewHolder = adapter.onCreateViewHolder(rvParent, ViewHolderType.ITEM.getType());
        adapter.onBindViewHolder(viewHolder, 0);

        // then
        assertThat(viewHolder).isExactlyInstanceOf(ShoppingItemAdapter.ItemViewHolder.class);
        ShoppingItemAdapter.ItemViewHolder shoppingItemView = (ShoppingItemAdapter.ItemViewHolder) viewHolder;
        assertThat(shoppingItemView.listItemNameTextView.getText().toString()).isEqualTo(shoppingItem.getName());
    }

}