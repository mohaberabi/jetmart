package com.mohaberabi.jetmart.features.item.domain.source

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.item.domain.model.ItemModel

interface ItemRemoteDataSource {

    suspend fun getItem(itemId: String): AppResult<ItemModel, DataError>
    suspend fun getItemsWhereIn(itemsIds: List<String>): AppResult<List<ItemModel>, DataError>
}