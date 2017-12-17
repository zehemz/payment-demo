package com.lucasbais.paymentdemo.datasource.database.entity

import android.arch.persistence.room.*
import io.reactivex.annotations.NonNull


@Entity(tableName = "bank",
        foreignKeys = [(ForeignKey(entity = PaymentMethodEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("paymentId"),
                onDelete = ForeignKey.CASCADE))],
        indices = [(Index(value = ["paymentId"]))])

class IssuerEntity {

    @PrimaryKey
    @NonNull
    lateinit var id: String
    lateinit var paymentId: String
    lateinit var name: String
    lateinit var thumbnail: String

    //needed for dta binding
    constructor()

    @Ignore
    constructor(id: String, name: String, paymentId: String, thumbnail: String) {
        this.id = id
        this.name = name
        this.paymentId = paymentId
        this.thumbnail = thumbnail
    }
}