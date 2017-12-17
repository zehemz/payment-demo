package com.lucasbais.paymentdemo.ui.issuer

import com.lucasbais.paymentdemo.datasource.database.entity.IssuerEntity

interface IssuerCallback {
    fun onClick(issuer: IssuerEntity)
}