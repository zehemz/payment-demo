package com.lucasbais.paymentdemo.datasource.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.lucasbais.paymentdemo.datasource.database.entity.IssuerEntity

@Dao
interface IssuerDao {
    @Query("SELECT * FROM bank where paymentId = :paymentId")
    fun getBanks(paymentId: String): LiveData<List<IssuerEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: List<IssuerEntity>)
}