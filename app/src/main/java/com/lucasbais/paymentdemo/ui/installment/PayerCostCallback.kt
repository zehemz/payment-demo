package com.lucasbais.paymentdemo.ui.installment

import com.lucasbais.paymentdemo.datasource.database.entity.PayerCostEntity


interface PayerCostCallback {

    fun onClick(payerCost: PayerCostEntity)
}