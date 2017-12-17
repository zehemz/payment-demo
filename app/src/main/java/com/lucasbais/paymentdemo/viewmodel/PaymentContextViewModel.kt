package com.lucasbais.paymentdemo.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.lucasbais.paymentdemo.PaymentService
import com.lucasbais.paymentdemo.datasource.database.entity.BankEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity
import javax.inject.Inject
import javax.inject.Singleton

// TODO no se esta haciendo uso de la magia de Viewmodel, porque constructores custom
// TODO es necesario algo como esto, pero de momento con el contexto a nivel singleton cumple la misma funcion y tecnicamente hablando es mas performante
// https://github.com/googlesamples/android-architecture-components/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/viewmodel/GithubViewModelFactory.java
// TODO algo a chequear es ver que pasa con el lifecycle, cuando te salis del ejemplo de android se pudre todo.
@Singleton
class PaymentContextViewModel @Inject internal constructor(private val paymentService: PaymentService) : ViewModel() {

    private val priceToPay: MutableLiveData<String> = MutableLiveData()
    private val creditCardId: MutableLiveData<String> = MutableLiveData()
    private val bankId: MutableLiveData<String> = MutableLiveData()

    fun getPriceToPay(): LiveData<String> {
        return priceToPay
    }

    fun getCreditCardSelected(): LiveData<String> {
        return creditCardId
    }

    fun getBankId(): LiveData<String> {
        return bankId
    }

    fun getCreditCardPaymentMethods(): LiveData<List<PaymentMethodEntity>> {
        return paymentService.getCreditCardPaymentOptions()
    }

    fun getBanks(): LiveData<List<BankEntity>> {
        return paymentService.getIssuers(creditCardId.value!!)
    }

    fun setPaymentMethodSelected(paymentMethodEntity: PaymentMethodEntity) {
        creditCardId.value = paymentMethodEntity.id
    }

    fun setBankSelected(bank: BankEntity) {
        bankId.value = bank.id
    }

    fun setPriceToPay(price: String) {
        priceToPay.value = price
    }
}