package com.mohaberabi.jetmart.features.item.domain.repository

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.item.domain.model.ItemModel

interface ItemRepository {


    suspend fun getItem(itemId: String): AppResult<ItemModel, DataError>
}