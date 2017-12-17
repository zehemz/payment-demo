package com.lucasbais.paymentdemo.ui.bank

import com.lucasbais.paymentdemo.datasource.database.entity.BankEntity

interface BankCallback {
    fun onClick(bank: BankEntity)
}