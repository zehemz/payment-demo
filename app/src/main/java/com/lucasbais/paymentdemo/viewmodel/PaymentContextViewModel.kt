package com.lucasbais.paymentdemo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.VisibleForTesting
import com.lucasbais.paymentdemo.datasource.PaymentService
import com.lucasbais.paymentdemo.datasource.entity.IssuerEntity
import com.lucasbais.paymentdemo.datasource.entity.PayerCostEntity
import com.lucasbais.paymentdemo.datasource.entity.PaymentMethodEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentContextViewModel @Inject internal constructor(private val paymentService: PaymentService)
    : ViewModel() {

    private lateinit var amount: String
    private lateinit var issuer: IssuerEntity
    private lateinit var payerCost: PayerCostEntity
    private lateinit var payingMethod: PaymentMethodEntity

    @VisibleForTesting
    fun getCreditCardPaymentMethods(): LiveData<List<PaymentMethodEntity>> {
        return paymentService.getCreditCardPaymentOptions()
    }

    @VisibleForTesting
    fun getIssuers(): LiveData<List<IssuerEntity>> {
        return paymentService.getIssuers(payingMethod.id)
    }

    @VisibleForTesting
    fun getPayerCosts(): LiveData<List<PayerCostEntity>> {
        val toPay: Double = amount.replace(Regex("[^\\d.]"), "").toDouble()
        return paymentService.getInstallment(payingMethod.id, issuer.id, toPay)
    }

    @VisibleForTesting
    fun paymentMethodSelected(paymentMethodEntity: PaymentMethodEntity) {
        payingMethod = paymentMethodEntity
    }

    @VisibleForTesting
    fun issuerSelected(issuer: IssuerEntity) {
        this.issuer = issuer
    }

    @VisibleForTesting
    fun setPriceToPay(price: String) {
        amount = price
    }

    @VisibleForTesting
    fun payerCostSelected(payerCost: PayerCostEntity) {
        this.payerCost = payerCost
    }

    @VisibleForTesting
    fun getCompleteUserInput(): String {
        return String.format("Amount: %s\nIssuer: %s\nPayer cost: %s", amount,
                issuer.name,
                payerCost.recommendedMessage)
    }

    override fun onCleared() {
        paymentService.dispose()
    }
}
