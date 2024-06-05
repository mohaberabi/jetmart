package com.mohaberabi.jetmart.features.listing.presentation.screen

import JetMartScaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAppBar
import com.mohaberabi.jetmart.core.presentation.compose.JetMartError
import com.mohaberabi.jetmart.core.presentation.compose.JetMartPlaceHolder
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.cart.domain.model.CartModel
import com.mohaberabi.jetmart.features.cart.presentation.compose.CartBottomBar
import com.mohaberabi.jetmart.features.listing.presentation.compose.ListingCategoryLazyRow
import com.mohaberabi.jetmart.features.listing.presentation.compose.ListingItemCard
import com.mohaberabi.jetmart.features.listing.presentation.compose.ListingScreenShimmer
import com.mohaberabi.jetmart.features.listing.presentation.viemwodel.ListingActions
import com.mohaberabi.jetmart.features.listing.presentation.viemwodel.ListingCategoryState
import com.mohaberabi.jetmart.features.listing.presentation.viemwodel.ListingItemsState
import com.mohaberabi.jetmart.features.listing.presentation.viemwodel.ListingViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun ListingScreenRoot(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewmodel: ListingViewModel = koinViewModel(),
    onGoItemDetails: (String) -> Unit,
    onGoCart: () -> Unit

) {

    val cart by viewmodel.cartState.collectAsStateWithLifecycle()
    val listingCategoryState = viewmodel.parentCategoriesState
    val listingItemsState by viewmodel.itemsListingState.collectAsStateWithLifecycle()
    ListingScreen(
        modifier = modifier,
        listingCategoryState = listingCategoryState,
        onAction = { action ->
            when (action) {
                ListingActions.OnCartClicked -> onGoCart()
                is ListingActions.OnItemClicked -> onGoItemDetails(action.id)
                else -> viewmodel.onAction(action)
            }

        },
        listingItemState = listingItemsState,
        onBackClick = onBackClick,
        cart = cart
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingScreen(
    modifier: Modifier = Modifier,
    listingCategoryState: ListingCategoryState,
    onAction: (ListingActions) -> Unit,
    listingItemState: ListingItemsState,
    onBackClick: () -> Unit = {},
    cart: CartModel
) {

    val categoriesLazyListState = rememberLazyListState()
    LaunchedEffect(key1 = true) {
        categoriesLazyListState.scrollToItem(listingCategoryState.index)
    }
    JetMartScaffold(
        modifier = modifier,
        bottomAppBar = {
            if (listingItemState is ListingItemsState.Done)
                CartBottomBar(
                    cartTotal = cart.cartTotal,
                    onClick = { onAction(ListingActions.OnCartClicked) }
                )
        },
        topAppBar = {
            JetMartAppBar(
                onBackClick = onBackClick,
                color = MaterialTheme.colorScheme.primary,
                navigationIconColor = MaterialTheme.colorScheme.onPrimary,
                showBackButton = true,
                titleContent = {

                    ListingCategoryLazyRow(
                        lazyListState = categoriesLazyListState,
                        onClick = { cat, index ->
                            onAction(ListingActions.OnParentCategoryClicked(index))
                        },
                        choosedId = listingCategoryState.currentCategoryId,
                        categories = listingCategoryState.parentCategories
                    )
                },
                isCenter = false
            )

        }
    ) { padding ->

        when (listingItemState) {
            is ListingItemsState.Done -> {


                if (listingItemState.listing.items.isEmpty()
                    || listingItemState.listing.childCategories.isEmpty()
                ) {
                    JetMartPlaceHolder(modifier = Modifier.padding(padding))
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .padding(padding)
                            .padding(Spacing.lg)
                    ) {
                        items(
                            listingItemState.listing.items,
                        ) { item ->
                            ListingItemCard(
                                item = item,
                                onClick = {
                                    onAction(ListingActions.OnItemClicked(item.itemId))
                                }
                            )
                        }

                    }
                }

            }

            is ListingItemsState.Error -> JetMartError(errorTitle = listingItemState.error)
            else -> ListingScreenShimmer(modifier = Modifier.padding(padding))
        }


    }

}

@Preview
@Composable
private fun ListingScreenPreview() {
    JetMartTheme {

        ListingScreen(
            listingCategoryState = ListingCategoryState(),
            onAction = {},
            cart = CartModel(),
            listingItemState = ListingItemsState.Loading
        )
    }
}
