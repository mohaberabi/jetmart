package com.mohaberabi.jetmart.features.address.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.address.domain.source.local.FavoriteAddressLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class FavoriteAddressDataStore(
    private val datastore: DataStore<Preferences>,
    private val dispatchers: DispatchersProvider
) : FavoriteAddressLocalDataSource {


    companion object {

        private val FAV_ADDRESS_KEY = stringPreferencesKey("favAddressKey")
    }

    override suspend fun saveAddress(
        address: AddressModel,
    ) {
        withContext(dispatchers.io) {
            datastore.edit { prefs ->
                prefs[FAV_ADDRESS_KEY] = Json.encodeToString(address.toSerialized())
            }
        }

    }

    override fun getFavoriteAddress(): Flow<AddressModel?> {
        return datastore.data.map { prefs ->
            prefs[FAV_ADDRESS_KEY]?.let {
                Json.decodeFromString<SerializableAddress>(it).toAddress()
            }
        }.flowOn(dispatchers.io)
    }
}


@Serializable
private data class SerializableAddress(
    val id: String,
    val lat: Double,
    val lng: Double,
    val address: String,
    val label: String,
    val location: String
)

private fun SerializableAddress.toAddress(): AddressModel {
    return AddressModel(
        id = id,
        label = label,
        lat = lat,
        lng = lng,
        location = location,
        address = address
    )
}

private fun AddressModel.toSerialized(): SerializableAddress {
    return SerializableAddress(
        id = id,
        label = label,
        lat = lat,
        lng = lng,
        location = location,
        address = address
    )
}