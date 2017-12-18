package com.lucasbais.paymentdemo.ui.installment

import com.lucasbais.paymentdemo.datasource.entity.PayerCostEntity


interface PayerCostCallback {

    fun onClick(payerCost: PayerCostEntity)
}