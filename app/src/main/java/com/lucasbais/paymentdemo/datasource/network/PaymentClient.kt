package com.lucasbais.paymentdemo.datasource.network

import com.lucasbais.paymentdemo.datasource.database.entity.IssuerEntity
import com.lucasbais.paymentdemo.datasource.database.entity.InstallmentEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PaymentClient {
    @GET("v1/payment_methods")
    fun getPaymentMethods(): Observable<List<PaymentMethodEntity>>

    @GET("v1/payment_methods/card_issuers")
    fun getIssuers(@Query("payment_method_id") paymentMethodId: String): Observable<List<IssuerEntity>>

    @GET("v1/payment_methods/installments")
    fun getInstallments(@Query("payment_method_id") paymentMethodId: String,
            //TODO: preguntar porque el issuer es con .
                        @Query("issuer.id") issuerId: String,
                        @Query("amount") amount: Double): Observable<List<InstallmentEntity>>
}