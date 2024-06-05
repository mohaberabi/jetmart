package com.mohaberabi.jetmart.features.item.data.repository

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.item.domain.model.ItemModel
import com.mohaberabi.jetmart.features.item.domain.repository.ItemRepository
import com.mohaberabi.jetmart.features.item.domain.source.ItemRemoteDataSource

class DefaultItemRepository(
    private val itemRemoteDataSource: ItemRemoteDataSource,
) : ItemRepository {
    override suspend fun getItem(itemId: String): AppResult<ItemModel, DataError> =
        itemRemoteDataSource.getItem(itemId)
}