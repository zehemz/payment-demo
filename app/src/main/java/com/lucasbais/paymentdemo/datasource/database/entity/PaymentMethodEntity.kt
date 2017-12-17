package com.lucasbais.paymentdemo.datasource.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import io.reactivex.annotations.NonNull


@Entity(tableName = "payment_methods")
class PaymentMethodEntity {

    @PrimaryKey
    @NonNull
    lateinit var id: String
    lateinit var name: String
    lateinit var paymentTypeId: String
    lateinit var thumbnail: String

    //needed for dta binding
    constructor()

    @Ignore
    constructor(id: String, name: String, paymentTypeId: String, thumbnail: String) {
        this.id = id
        this.name = name
        this.paymentTypeId = paymentTypeId
        this.thumbnail = thumbnail
    }
}