package com.mohaberabi.jetmart.features.address.data.source.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.EmptyDataResult
import com.mohaberabi.jetmart.core.util.const.EndPoints
import com.mohaberabi.jetmart.core.util.error.DataError
import com.mohaberabi.jetmart.core.util.error.fromFirebaseFirestoreException
import com.mohaberabi.jetmart.features.address.data.source.remote.dto.AddressDto
import com.mohaberabi.jetmart.features.address.data.source.remote.dto.toAddressDto
import com.mohaberabi.jetmart.features.address.data.source.remote.dto.toAddressModel
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.address.domain.source.remote.AddressRemoteDataSource
import kotlinx.coroutines.tasks.await

class FirebaseAddressRemoteDataSource(
    private val firestore: FirebaseFirestore,
) : AddressRemoteDataSource {
    override suspend fun addAddress(
        uid: String,
        address: AddressModel
    ): EmptyDataResult<DataError> {
        return try {
            addresses(uid).document(address.id).set(address.toAddressDto()).await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }

    override suspend fun deleteAddress(
        uid: String, addressId: String,
    ): EmptyDataResult<DataError> {
        return try {
            addresses(uid).document(addressId).delete().await()
            AppResult.Done(Unit)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }

    override suspend fun getAllAddresses(
        uid: String
    ): AppResult<List<AddressModel>, DataError> {
        return try {
            val addreeses =
                addresses(uid).get().await().map { it.toObject<AddressDto>().toAddressModel() }
            AppResult.Done(addreeses)
        } catch (e: FirebaseFirestoreException) {
            e.printStackTrace()
            AppResult.Error(e.fromFirebaseFirestoreException())
        } catch (e: Exception) {
            e.printStackTrace()
            AppResult.Error(DataError.Network.UNKNOWN_ERROR)
        }
    }


    private fun addresses(uid: String): CollectionReference =
        firestore.collection(EndPoints.USERS).document(uid).collection(EndPoints.ADDRESSES)
}