package com.mohaberabi.jetmart.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mohaberabi.jetmart.JetMartApplication
import com.mohaberabi.jetmart.core.data.geocoder.AndroidJetMartGeoCoder
import com.mohaberabi.jetmart.core.data.repository.DefautJetMartRepository
import com.mohaberabi.jetmart.core.data.source.JetMartDataStore
import com.mohaberabi.jetmart.core.domain.repository.JetMartPrefsRepository
import com.mohaberabi.jetmart.core.domain.source.local.JeetMartPrefsLocalDataSource
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.core.util.JetMartDispatcherProvider
import com.mohaberabi.jetmart.core.data.locaiton.AndroidJetMartLocationProvider
import com.mohaberabi.jetmart.core.data.network_info.AndroidNetworkInfo
import com.mohaberabi.jetmart.core.domain.geocoder.JetMartGeoCoder
import com.mohaberabi.jetmart.core.domain.location.JetMartLocationProvider
import com.mohaberabi.jetmart.core.domain.network_info.NetworkInfo
import com.mohaberabi.jetmart.features.settings.data.DefaultSettingsRepository
import com.mohaberabi.jetmart.features.settings.domain.repository.SettingsRepository
import com.mohaberabi.jetmart.jetmart.activity.viewmodel.JetMartActivityViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "jetmart")
val appModule = module {
    single<DataStore<Preferences>> {
        androidApplication().dataStore
    }
    single<FirebaseFirestore> {
        Firebase.firestore
    }
    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }
    single<DispatchersProvider> {
        JetMartDispatcherProvider()
    }
    single<JeetMartPrefsLocalDataSource> {
        JetMartDataStore(get(), get())
    }
    single<JetMartPrefsRepository> {
        DefautJetMartRepository(get())
    }
    single<CoroutineScope> {

        (androidApplication() as JetMartApplication).applicationScope
    }
    single<JetMartLocationProvider> {
        AndroidJetMartLocationProvider(
            get(),
            get()
        )
    }
    singleOf(::DefaultSettingsRepository).bind<SettingsRepository>()

    single<JetMartGeoCoder> {
        AndroidJetMartGeoCoder(get(), get())
    }
    single<NetworkInfo> {
        AndroidNetworkInfo(get(), get())
    }
    viewModelOf(::JetMartActivityViewModel)
}