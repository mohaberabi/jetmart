package com.mohaberabi.jetmart.core.database.di

import androidx.room.Room
import com.mohaberabi.jetmart.core.database.daos.AddressDao
import com.mohaberabi.jetmart.core.database.daos.CartDao
import com.mohaberabi.jetmart.core.database.daos.DeletedAddressDao
import com.mohaberabi.jetmart.core.database.daos.DeletedCartItemDao
import com.mohaberabi.jetmart.core.database.daos.OrderOverViewDao
import com.mohaberabi.jetmart.core.database.daos.PendingAddressDao
import com.mohaberabi.jetmart.core.database.daos.PendingCartItemDao
import com.mohaberabi.jetmart.core.database.db.JetMartDatabase
import com.mohaberabi.jetmart.core.util.const.AppConst
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val databaseModule = module {


    single<JetMartDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            JetMartDatabase::class.java,
            AppConst.DATABASE_NAME,
        ).fallbackToDestructiveMigration().build()

    }


    single<AddressDao> {
        get<JetMartDatabase>().addressDao()
    }


    single<CartDao> {
        get<JetMartDatabase>().cartDao()
    }

    single<PendingCartItemDao> {
        get<JetMartDatabase>().pendingCartItemDao()

    }
    single<DeletedCartItemDao> {
        get<JetMartDatabase>().deletedCartItemDao()
    }

    single<DeletedAddressDao> {
        get<JetMartDatabase>().deletedAddressDao()
    }

    single<PendingAddressDao> {
        get<JetMartDatabase>().pendingAddressDao()
    }
    single<OrderOverViewDao> {
        get<JetMartDatabase>().ordersDao()
    }
}