package com.mohaberabi.jetmart.features.home.presentation.screen

import JetMartScaffold
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartError
import com.mohaberabi.jetmart.core.presentation.compose.JetMartLoader
import com.mohaberabi.jetmart.core.presentation.compose.JetMartPlaceHolder
import com.mohaberabi.jetmart.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.core.util.extensions.spannedItem
import com.mohaberabi.jetmart.features.category.domain.model.mapToListingCategoryModel
import com.mohaberabi.jetmart.features.category.presetnation.compsoe.CategoryCard
import com.mohaberabi.jetmart.features.home.presentation.compose.AddressTopBar
import com.mohaberabi.jetmart.features.home.presentation.compose.HomeScreenShimmer
import com.mohaberabi.jetmart.features.home.presentation.viewmodel.HomeActions
import com.mohaberabi.jetmart.features.home.presentation.viewmodel.HomeCategoriesState
import com.mohaberabi.jetmart.features.home.presentation.viewmodel.HomeScreenEvents
import com.mohaberabi.jetmart.features.home.presentation.viewmodel.HomeScreenViewModel
import com.mohaberabi.jetmart.features.listing.domain.model.ListingCategoryModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = koinViewModel(),
    onGoAddressLocation: () -> Unit,
    onCategoryClick: (
        Int,
        List<ListingCategoryModel>
    ) -> Unit,
    onGoSignIn: () -> Unit,
    onShowAddressPicker: () -> Unit,
) {

    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            HomeScreenEvents.GoSignIn -> onGoSignIn()
            HomeScreenEvents.ShowAddressPicker -> onShowAddressPicker()
        }
    }
    val categoriesState by viewModel.categoriesState.collectAsStateWithLifecycle()
    HomeScreen(
        modifier = modifier,
        categoriesState = categoriesState,
        onAction = { action ->
            when (action) {
                is HomeActions.OnCategoryClick -> onCategoryClick(
                    action.index,
                    action.parentCategories
                )

                else -> viewModel.onAction(action)
            }

        }
    )
}


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    categoriesState: HomeCategoriesState,
    onAction: (HomeActions) -> Unit = {}
) {
    JetMartScaffold(
        modifier = modifier,
    ) { _ ->

        when (categoriesState) {
            is HomeCategoriesState.Done -> {

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Center
                ) {
                    spannedItem(3) {
                        AddressTopBar(
                            onPickAddress = {
                                onAction(HomeActions.OnAddressClicked)
                            }
                        )
                    }
                    spannedItem(3) {
                        Text(
                            modifier = Modifier.padding(Spacing.md),
                            text = stringResource(R.string.shop_by_category),
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                    if (categoriesState.categories.isEmpty()) {
                        spannedItem(1) {
                            JetMartPlaceHolder()
                        }
                    } else {
                        items(categoriesState.categories.size) { index ->
                            val cat = categoriesState.categories[index]
                            CategoryCard(
                                category = cat,
                                onClick = {
                                    onAction(
                                        HomeActions.OnCategoryClick(
                                            index = index,
                                            parentCategories = categoriesState.categories.mapToListingCategoryModel(),
                                        )
                                    )
                                },
                            )
                        }

                    }


                }
            }

            is HomeCategoriesState.Error -> JetMartError(
                errorTitle = categoriesState.error,
                onRetry = { onAction(HomeActions.OnRetry) },
                errorSubtitle = stringResource(R.string.please_try_again),
            )

            else -> HomeScreenShimmer()
        }


    }
}


@Preview
@Composable
private fun PreviewHomeScreen() {

    JetMartTheme {
        HomeScreen(
            categoriesState = HomeCategoriesState.Loading,
        )
    }
}

