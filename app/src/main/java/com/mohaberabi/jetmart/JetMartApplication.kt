package com.mohaberabi.jetmart

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.initialize
import com.mohaberabi.jetmart.core.database.di.databaseModule
import com.mohaberabi.jetmart.core.di.appModule
import com.mohaberabi.jetmart.features.address.di.addressModule
import com.mohaberabi.jetmart.features.auth.di.authModule
import com.mohaberabi.jetmart.features.cart.di.cartModule
import com.mohaberabi.jetmart.features.category.di.categoryModule
import com.mohaberabi.jetmart.features.checkout.di.checkoutModule
import com.mohaberabi.jetmart.features.home_layout.homeLayoutModule
import com.mohaberabi.jetmart.features.item.di.itemModule
import com.mohaberabi.jetmart.features.listing.di.listingModule
import com.mohaberabi.jetmart.features.order.di.ordersModule
import com.mohaberabi.jetmart.features.profile.di.profileModule
import com.mohaberabi.jetmart.features.settings.di.settingsModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import kotlin.math.max


class JetMartApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidLogger()
            androidContext(this@JetMartApplication)
            workManagerFactory()
            modules(
                databaseModule,
                appModule,
                authModule,
                settingsModule,
                profileModule,
                categoryModule,
                homeLayoutModule,
                listingModule,
                itemModule,
                cartModule,
                addressModule,
                checkoutModule,
                ordersModule
            )
        }
    }

}


