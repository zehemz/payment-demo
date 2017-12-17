package com.lucasbais.paymentdemo.datasource.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.support.annotation.VisibleForTesting
import com.lucasbais.paymentdemo.datasource.database.dao.IssuerDao
import com.lucasbais.paymentdemo.datasource.database.dao.PaymentMethodDao
import com.lucasbais.paymentdemo.datasource.database.entity.IssuerEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity


@Database(entities = [PaymentMethodEntity::class, IssuerEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun paymentMethod(): PaymentMethodDao
    abstract fun issuers(): IssuerDao

    companion object {

        @VisibleForTesting
        private val DATABASE_NAME = "payment-database"

        fun create(context: Context): AppDatabase = Room.databaseBuilder(context,
                AppDatabase::class.java, DATABASE_NAME).build()
    }
}
