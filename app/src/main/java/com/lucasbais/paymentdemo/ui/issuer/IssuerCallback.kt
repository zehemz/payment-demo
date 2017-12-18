package com.lucasbais.paymentdemo.ui.issuer

import com.lucasbais.paymentdemo.datasource.entity.IssuerEntity

interface IssuerCallback {
    fun onClick(issuer: IssuerEntity)
}