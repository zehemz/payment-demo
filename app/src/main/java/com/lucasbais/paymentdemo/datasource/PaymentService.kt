package com.lucasbais.paymentdemo.datasource

import android.arch.lifecycle.LiveData
import com.lucasbais.paymentdemo.datasource.entity.IssuerEntity
import com.lucasbais.paymentdemo.datasource.entity.PayerCostEntity
import com.lucasbais.paymentdemo.datasource.entity.PaymentMethodEntity

interface PaymentService {

    fun getCreditCardPaymentOptions(): LiveData<List<PaymentMethodEntity>>

    fun getIssuers(paymentId: String): LiveData<List<IssuerEntity>>

    fun getInstallment(paymentMethodId: String,
                       issuerId: String,
                       amount: Double): LiveData<List<PayerCostEntity>>

    fun dispose()
}