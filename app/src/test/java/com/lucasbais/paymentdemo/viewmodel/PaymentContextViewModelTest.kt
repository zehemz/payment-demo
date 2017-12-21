package com.lucasbais.paymentdemo.viewmodel

import android.arch.lifecycle.Observer
import com.lucasbais.paymentdemo.datasource.PaymentService
import com.lucasbais.paymentdemo.datasource.entity.PaymentMethodEntity
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PaymentContextViewModelTest {

    @Mock
    private lateinit var paymentService: PaymentService

    @Mock
    private lateinit var observerPaymentMethod : Observer<List<PaymentMethodEntity>>

    private lateinit var paymentContextViewModel: PaymentContextViewModel

    @Before
    fun setUp() {
        paymentContextViewModel = PaymentContextViewModel(paymentService)
    }

    @Test
    fun testSettingPriceDoNotTriggerEvents() {
        paymentContextViewModel.setPriceToPay("$192")
        verifyNoMoreInteractions(paymentService)
    }

    @Test
    fun testPayingMethodsAreReturned() {
    }

    @Test
    fun getCreditCardPaymentMethods() {
    }

    @Test
    fun getIssuers() {
    }

    @Test
    fun getPayerCosts() {
    }

    @Test
    fun paymentMethodSelected() {
    }

    @Test
    fun issuerSelected() {
    }

    @Test
    fun payerCostSelected() {
    }

    @Test
    fun getCompleteUserInput() {
    }

    @Test
    fun onCleared() {
    }

}