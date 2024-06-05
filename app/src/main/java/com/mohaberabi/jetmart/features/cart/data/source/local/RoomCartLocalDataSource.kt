package com.mohaberabi.jetmart.features.cart.data.source.local

import android.database.sqlite.SQLiteFullException
import com.mohaberabi.jetmart.core.database.daos.CartDao
import com.mohaberabi.jetmart.core.database.entity.toCartItem
import com.mohaberabi.jetmart.core.database.entity.toCartItemEntity
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import com.mohaberabi.jetmart.features.cart.domain.source.local.CartLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomCartLocalDataSource(
    private val cartItemDao: CartDao,
) : CartLocalDataSource {
    override fun getAllCartItems(): Flow<List<CartItemModel>> =
        cartItemDao.getAllItems().map { list -> list.map { it.toCartItem() } }

    override suspend fun insertAllCartItems(
        items: List<CartItemModel>,
    ): EmptyDataResult<DataError.Local> {
        return try {
            cartItemDao.insertAllCartItems(items.map { it.toCartItemEntity() })
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)

        }
    }

    override suspend fun addToCart(
        item: CartItemModel
    ): EmptyDataResult<DataError.Local> {
        return try {
            cartItemDao.addToCart(item.toCartItemEntity())
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)

        }
    }

    override suspend fun removeCartItem(
        itemId: String,
    ): EmptyDataResult<DataError.Local> {
        return try {
            cartItemDao.deleteItem(itemId)
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)

        }
    }

    override suspend fun updateCartItem(item: CartItemModel): EmptyDataResult<DataError.Local> {
        return try {
            cartItemDao.addToCart(item.toCartItemEntity())
            AppResult.Done(Unit)
        } catch (e: SQLiteFullException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)

        }
    }


    override suspend fun clearCart() = cartItemDao.clearCart()
    override fun getItemById(itemId: String): Flow<CartItemModel?> {
        return cartItemDao.getItemById(itemId).map {
            it?.toCartItem()
        }
    }

    override fun getCartLength(): Flow<Int> = cartItemDao.getCartLength()
}