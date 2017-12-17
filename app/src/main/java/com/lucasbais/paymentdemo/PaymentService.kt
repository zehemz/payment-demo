package com.lucasbais.paymentdemo

import android.arch.lifecycle.LiveData
import com.lucasbais.paymentdemo.datasource.database.entity.IssuerEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PayerCostEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity

interface PaymentService {

    fun getCreditCardPaymentOptions(): LiveData<List<PaymentMethodEntity>>

    fun getIssuers(paymentId: String): LiveData<List<IssuerEntity>>

    fun getInstallment(paymentMethodId: String,
                       issuerId: String,
                       amount: Double): LiveData<List<PayerCostEntity>>

    fun dispose()
}