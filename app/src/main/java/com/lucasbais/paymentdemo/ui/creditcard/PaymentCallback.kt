package com.lucasbais.paymentdemo.ui.creditcard

import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity

interface PaymentCallback {
    fun onClick(paymentMethodEntity: PaymentMethodEntity)
}