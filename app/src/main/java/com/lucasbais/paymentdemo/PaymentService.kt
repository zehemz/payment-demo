package com.lucasbais.paymentdemo

import android.arch.lifecycle.LiveData
import com.lucasbais.paymentdemo.datasource.database.entity.BankEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity


interface PaymentService {

    fun getCreditCardPaymentOptions(): LiveData<List<PaymentMethodEntity>>

    fun getIssuers(paymentId:String):LiveData<List<BankEntity>>
}