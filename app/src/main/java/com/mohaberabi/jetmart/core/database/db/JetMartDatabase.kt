package com.mohaberabi.jetmart.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohaberabi.jetmart.core.database.daos.AddressDao
import com.mohaberabi.jetmart.core.database.daos.CartDao
import com.mohaberabi.jetmart.core.database.daos.DeletedAddressDao
import com.mohaberabi.jetmart.core.database.daos.DeletedCartItemDao
import com.mohaberabi.jetmart.core.database.daos.OrderOverViewDao
import com.mohaberabi.jetmart.core.database.daos.PendingAddressDao
import com.mohaberabi.jetmart.core.database.daos.PendingCartItemDao
import com.mohaberabi.jetmart.core.database.entity.AddressEntity
import com.mohaberabi.jetmart.core.database.entity.CartItemEntity
import com.mohaberabi.jetmart.core.database.entity.DeleteCartEntity
import com.mohaberabi.jetmart.core.database.entity.DeletedAddressEntity
import com.mohaberabi.jetmart.core.database.entity.OrderOverviewEntity
import com.mohaberabi.jetmart.core.database.entity.PendingAddressEntity
import com.mohaberabi.jetmart.core.database.entity.PendingCartItemEntity


@Database(
    entities = [
        CartItemEntity::class,
        DeleteCartEntity::class,
        PendingCartItemEntity::class,
        AddressEntity::class,
        PendingAddressEntity::class,
        DeletedAddressEntity::class,
        OrderOverviewEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
abstract class JetMartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun pendingCartItemDao(): PendingCartItemDao
    abstract fun deletedCartItemDao(): DeletedCartItemDao
    abstract fun addressDao(): AddressDao
    abstract fun pendingAddressDao(): PendingAddressDao
    abstract fun deletedAddressDao(): DeletedAddressDao
    abstract fun ordersDao(): OrderOverViewDao
}