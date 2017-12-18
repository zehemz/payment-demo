package com.lucasbais.paymentdemo.viewmodel

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.OnLifecycleEvent
import com.lucasbais.paymentdemo.datasource.PaymentService
import com.lucasbais.paymentdemo.datasource.entity.IssuerEntity
import com.lucasbais.paymentdemo.datasource.entity.PayerCostEntity
import com.lucasbais.paymentdemo.datasource.entity.PaymentMethodEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentContextViewModel @Inject internal constructor(private val paymentService: PaymentService) : LifecycleObserver {

    private lateinit var amount: String
    private lateinit var issuer: IssuerEntity
    private lateinit var payerCost: PayerCostEntity
    private lateinit var payingMethod: PaymentMethodEntity

    fun getCreditCardPaymentMethods(): LiveData<List<PaymentMethodEntity>> {
        return paymentService.getCreditCardPaymentOptions()
    }

    fun getIssuers(): LiveData<List<IssuerEntity>> {
        return paymentService.getIssuers(payingMethod.id)
    }

    fun getPayerCosts(): LiveData<List<PayerCostEntity>> {
        val toPay: Double = amount.replace(Regex("[^\\d.]"), "").toDouble()
        return paymentService.getInstallment(payingMethod.id, issuer.id, toPay)
    }

    fun paymentMethodSelected(paymentMethodEntity: PaymentMethodEntity) {
        payingMethod = paymentMethodEntity
    }

    fun issuerSelected(issuer: IssuerEntity) {
        this.issuer = issuer
    }

    fun setPriceToPay(price: String) {
        amount = price
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disposeUseCases() {
        paymentService.dispose()
    }

    fun payerCostSelected(payerCost: PayerCostEntity) {
        this.payerCost = payerCost
    }

    fun getCompleteUserInput(): String {
        return String.format("Amount: %s\nIssuer: %s\nPayer cost: %s", amount,
                issuer.name,
                payerCost.recommendedMessage)
    }
}