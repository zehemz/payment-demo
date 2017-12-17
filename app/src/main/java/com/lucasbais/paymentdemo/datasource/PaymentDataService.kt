package com.lucasbais.paymentdemo.datasource

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.lucasbais.paymentdemo.PaymentService
import com.lucasbais.paymentdemo.datasource.database.AppDatabase
import com.lucasbais.paymentdemo.datasource.database.entity.InstallmentEntity
import com.lucasbais.paymentdemo.datasource.database.entity.IssuerEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PayerCostEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity
import com.lucasbais.paymentdemo.internal.interactors.*
import javax.inject.Inject


class PaymentDataService @Inject internal constructor(private val database: AppDatabase,
                                                      private val paymentOptionsUseCase: PaymentOptionsUseCase,
                                                      private val issuerUseCase: IssuerUseCase,
                                                      private val installmentUseCase: InstallmentUseCase) : PaymentService {

    private val creditCardPaymentMethodObservable: MediatorLiveData<List<PaymentMethodEntity>> = MediatorLiveData()
    private val payerCostsObserver: MediatorLiveData<List<PayerCostEntity>> = MediatorLiveData()
    private var issuerObservable: MediatorLiveData<List<IssuerEntity>> = MediatorLiveData()

    init {
        creditCardPaymentMethodObservable.addSource(database.paymentMethod()
                .getCreditCardPaymentMethods()) { values ->
            values?.let {
                creditCardPaymentMethodObservable.postValue(values)
            }
        }
    }

    override fun getCreditCardPaymentOptions(): LiveData<List<PaymentMethodEntity>> {
        retrieveAndStorePaymentMethods()
        return creditCardPaymentMethodObservable
    }

    override fun getIssuers(paymentId: String): LiveData<List<IssuerEntity>> {
        issuerObservable = MediatorLiveData()
        issuerObservable.value = null
        issuerObservable.addSource(database.issuers().getBanks(paymentId)) { values ->
            values?.let {
                issuerObservable.postValue(values)
            }
        }
        retrieveIssuerAndStore(paymentId)
        return issuerObservable
    }

    override fun getInstallment(paymentMethodId: String, issuerId: String, amount: Double): LiveData<List<PayerCostEntity>> {
        payerCostsObserver.value = null // Clear old values
        retrieveInstallment(paymentMethodId, issuerId, amount)
        return payerCostsObserver
    }

    private fun retrieveAndStorePaymentMethods() {
        paymentOptionsUseCase.execute(object : DefaultDisposableObserver<List<PaymentMethodEntity>>() {
            override fun onNext(t: List<PaymentMethodEntity>) {
                database.runInTransaction({
                    database.paymentMethod().insert(t)
                })
            }
        }, ParamsEmpty())
    }

    private fun retrieveInstallment(paymentMethodId: String, issuerId: String, amount: Double) {
        installmentUseCase.execute(object : DefaultDisposableObserver<InstallmentEntity>() {
            override fun onNext(t: InstallmentEntity) {
                payerCostsObserver.value = t.payerCosts
            }
        }, InstallmentUseCase.Params.create(paymentMethodId, issuerId, amount))
    }

    private fun retrieveIssuerAndStore(paymentId: String) {
        issuerUseCase.execute(object : DefaultDisposableObserver<List<IssuerEntity>>() {
            override fun onNext(t: List<IssuerEntity>) {
                database.runInTransaction({
                    t.map { value -> value.paymentId = paymentId }
                    database.issuers().insert(t)
                })
            }
        }, IssuerUseCase.Params.create(paymentId))
    }

    override fun dispose() {
        installmentUseCase.dispose()
        paymentOptionsUseCase.dispose()
        issuerUseCase.dispose()
    }
}