package com.mohaberabi.jetmart.features.listing.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jetmart.features.cart.presentation.navigation.navigateToCartScreen
import com.mohaberabi.jetmart.features.item.presentation.navigation.navigateToItemScreen
import com.mohaberabi.jetmart.features.listing.domain.model.ListingCategoryModel
import com.mohaberabi.jetmart.features.listing.presentation.screen.ListingScreenRoot
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class ListingRoute(
    val index: Int,
    val categoriesAsJson: String
) {
    fun decodedCategories(): List<ListingCategoryModel> {
        val list = Json.decodeFromString<List<String>>(categoriesAsJson)
        return list.map { Json.decodeFromString<ListingCategoryModel>(it) }
    }
}


//val ListingRouteArgsType = object : NavType<ListingRouteArgs>(
//    isNullableAllowed = false
//) {
//
//    override fun get(bundle: Bundle, key: String): ListingRouteArgs {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            return bundle.getParcelable(key, ListingRouteArgs::class.java)!!
//        } else {
//            return bundle.getParcelable<ListingRouteArgs>(key)!!
//        }
//
//    }
//
//    override fun parseValue(value: String): ListingRouteArgs {
//        return Json.decodeFromString<ListingRouteArgs>(value)
//    }
//
//    override fun put(bundle: Bundle, key: String, value: ListingRouteArgs) {
//        bundle.putParcelable(key, value)
//    }
//
//    override val name = "ListingRouteArgs"
//
//}

fun NavGraphBuilder.listingScreen(
    jetMartNavController: NavController,
) = composable<ListingRoute>(
) {
    ListingScreenRoot(
        onGoItemDetails = { jetMartNavController.navigateToItemScreen(it) },
        onBackClick = { jetMartNavController.popBackStack() },
        onGoCart = { jetMartNavController.navigateToCartScreen() }
    )
}

fun NavController.navigateToListingScreen(
    index: Int,
    categories: List<ListingCategoryModel>
) = navigate(
    ListingRoute(
        index = index,
        categoriesAsJson = Json.encodeToString(categories.map { Json.encodeToString(it) })
    )
)