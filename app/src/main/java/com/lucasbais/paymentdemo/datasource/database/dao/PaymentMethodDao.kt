/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lucasbais.paymentdemo.datasource.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity

@Dao
interface PaymentMethodDao {

    @Query("SELECT * FROM payment_methods")
    fun getPaymentMethods(): LiveData<List<PaymentMethodEntity>>

    @Query("select * from payment_methods where id = :searchedId")
    fun getPaymentMethod(searchedId: Int): LiveData<PaymentMethodEntity>

    @Query("SELECT * FROM payment_methods where paymentTypeId = 'credit_card'")
    fun getCreditCardPaymentMethods(): LiveData<List<PaymentMethodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: List<PaymentMethodEntity>)
}