package com.mohaberabi.jetmart.features.address.data.repository

import androidx.datastore.core.IOException
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.asEmptyResult
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.address.domain.repository.AddressRepository
import com.mohaberabi.jetmart.features.address.domain.source.local.AddressLocalDataSource
import com.mohaberabi.jetmart.features.address.domain.source.local.FavoriteAddressLocalDataSource
import com.mohaberabi.jetmart.features.address.domain.source.remote.AddressRemoteDataSource
import com.mohaberabi.jetmart.features.address.domain.sync.AddressSyncEnqueuer
import com.mohaberabi.jetmart.features.auth.domain.source.local.UserLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class OfflineFirstAddressRepository(
    private val addressRemoteDataSource: AddressRemoteDataSource,
    private val addressLocalDataSource: AddressLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val addressSyncEnqueuer: AddressSyncEnqueuer,
    private val appScope: CoroutineScope,
    private val favoriteAddressLocalDataSource: FavoriteAddressLocalDataSource
) : AddressRepository {


    override fun getAllAddresses(): Flow<List<AddressModel>> =
        addressLocalDataSource.getAllAddress()

    override suspend fun addAddress(
        address: AddressModel
    ): EmptyDataResult<DataError> {
        val userId = userLocalDataSource.getUserId()


        val localRes = addressLocalDataSource.addAddress(address)

        if (localRes !is AppResult.Done) {
            return localRes.asEmptyResult()
        }

        return when (val remoteRes =
            addressRemoteDataSource.addAddress(address = address, uid = userId!!)) {
            is AppResult.Done -> remoteRes.asEmptyResult()
            is AppResult.Error -> {
                appScope.launch {
                    addressSyncEnqueuer.enqueueSync(
                        AddressSyncEnqueuer.AddressSyncType.UpsertAddress(
                            address = address,
                            uid = userId
                        )
                    )
                }.join()
                AppResult.Done(Unit)
            }
        }

    }

    override suspend fun deleteAddress(
        id: String,
    ): EmptyDataResult<DataError> {


        val userId = userLocalDataSource.getUserId()!!

        addressLocalDataSource.deleteAddress(id)

        return when (val remoteRes =
            addressRemoteDataSource.deleteAddress(addressId = id, uid = userId)) {
            is AppResult.Done -> remoteRes.asEmptyResult()
            is AppResult.Error -> {
                appScope.launch {
                    addressSyncEnqueuer.enqueueSync(
                        AddressSyncEnqueuer.AddressSyncType.DeleteAddress(
                            uid = userId,
                            id = id
                        )
                    )
                }.join()
                AppResult.Done(Unit)
            }
        }
    }

    override suspend fun saveFavoriteAddress(address: AddressModel): EmptyDataResult<DataError> {
        return try {
            favoriteAddressLocalDataSource.saveAddress(address)
            AppResult.Done(Unit)
        } catch (e: IOException) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.IOException)
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun getFavoriteAddress(): Flow<AddressModel?> =
        favoriteAddressLocalDataSource.getFavoriteAddress()


    override suspend fun fetchAddresses(): EmptyDataResult<DataError> {

        val localAddressCount = addressLocalDataSource.getAddressCount()

        return if (localAddressCount > 0) {
            AppResult.Done(Unit)
        } else {
            val userId = userLocalDataSource.getUserId()
            when (val res = addressRemoteDataSource.getAllAddresses(userId!!)) {
                is AppResult.Done -> {
                    appScope.launch {
                        addressLocalDataSource.insertAllAddresses(res.data)
                    }.join()
                    AppResult.Done(Unit)
                }

                is AppResult.Error -> res.asEmptyResult()
            }

        }

    }
}