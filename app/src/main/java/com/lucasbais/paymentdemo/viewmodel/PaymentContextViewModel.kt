package com.lucasbais.paymentdemo.viewmodel

import android.arch.lifecycle.*
import com.lucasbais.paymentdemo.PaymentService
import com.lucasbais.paymentdemo.datasource.database.entity.IssuerEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PayerCostEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentContextViewModel @Inject internal constructor(private val paymentService: PaymentService) : LifecycleObserver {

    private val amount: MutableLiveData<String> = MutableLiveData()
    private val payingMethod: MutableLiveData<String> = MutableLiveData()
    private val issuer: MutableLiveData<String> = MutableLiveData()

    fun getPriceToPay(): LiveData<String> {
        return amount
    }

    fun getCreditCardSelected(): LiveData<String> {
        return payingMethod
    }

    fun getBankId(): LiveData<String> {
        return issuer
    }

    fun getCreditCardPaymentMethods(): LiveData<List<PaymentMethodEntity>> {
        return paymentService.getCreditCardPaymentOptions()
    }

    fun getIssuers(): LiveData<List<IssuerEntity>> {
        return paymentService.getIssuers(payingMethod.value!!)
    }

    fun getPayerCosts(): LiveData<List<PayerCostEntity>> {
        val toPay: Double = amount.value!!.replace(Regex("[^\\d.]"), "").toDouble()
        return paymentService.getInstallment(payingMethod.value!!, issuer.value!!, toPay)
    }

    fun setPaymentMethodSelected(paymentMethodEntity: PaymentMethodEntity) {
        payingMethod.value = paymentMethodEntity.id
    }

    fun setBankSelected(issuer: IssuerEntity) {
        this.issuer.value = issuer.id
    }

    fun setPriceToPay(price: String) {
        amount.value = price
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disposeUseCases() {
        paymentService.dispose()
    }

    fun setPayerCost(payerCost: PayerCostEntity) {

    }
}