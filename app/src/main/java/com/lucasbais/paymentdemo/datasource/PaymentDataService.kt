package com.lucasbais.paymentdemo.datasource

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.lucasbais.paymentdemo.PaymentService
import com.lucasbais.paymentdemo.datasource.database.AppDatabase
import com.lucasbais.paymentdemo.datasource.database.entity.BankEntity
import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity
import com.lucasbais.paymentdemo.datasource.network.PaymentClient
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Named


class PaymentDataService @Inject internal constructor(private val database: AppDatabase,
                                                      private val paymentClient: PaymentClient,
                                                      @Named("netExecutor") private val netExecutor: Executor,
                                                      @Named("diskExecutor") private val diskExecutor: Executor) : PaymentService {


    private val creditCardPaymentMethodObservable: MediatorLiveData<List<PaymentMethodEntity>> = MediatorLiveData()
    private var bankObservable: MediatorLiveData<List<BankEntity>>? = null
    private var disposable: CompositeDisposable = CompositeDisposable()

    init {
        creditCardPaymentMethodObservable.addSource(database.paymentMethod().getCreditCardPaymentMethods()) { values ->
            values?.let {
                creditCardPaymentMethodObservable.postValue(values)
            }
        }
    }

    override fun getIssuers(paymentId:String): LiveData<List<BankEntity>> {
        updateBanksFor(paymentId)
        if(bankObservable == null){
            bankObservable = MediatorLiveData()
            bankObservable?.addSource(database.banks().getBanks(paymentId)){
                    values -> values?.let {
                    bankObservable?.postValue(values)
                }
            }
        }

        return bankObservable!!
    }

    override fun getCreditCardPaymentOptions(): LiveData<List<PaymentMethodEntity>> {
        updatePaymentOptions()
        return creditCardPaymentMethodObservable
    }

    private fun updatePaymentOptions() {
        val netPaymentMethodObserver: Observable<List<PaymentMethodEntity>> = paymentClient.getPaymentMethods()
                .subscribeOn(Schedulers.from(netExecutor))
                .observeOn(Schedulers.from(diskExecutor))

        disposable.dispose()
        disposable = CompositeDisposable()
        disposable.add(netPaymentMethodObserver.subscribeWith(object : DisposableObserver<List<PaymentMethodEntity>>() {

            override fun onNext(values: List<PaymentMethodEntity>) {
                database.runInTransaction({
                    database.paymentMethod().insert(values)
                })
            }

            override fun onComplete() {}
            override fun onError(e: Throwable) {}
        }))
    }

    private fun updateBanksFor(paymentId: String){
        val netBankObserver: Observable<List<BankEntity>> = paymentClient.getIssuers(paymentId)
                .subscribeOn(Schedulers.from(netExecutor))
                .observeOn(Schedulers.from(diskExecutor))

        disposable.dispose()
        disposable = CompositeDisposable()
        disposable.add(netBankObserver.subscribeWith(object : DisposableObserver<List<BankEntity>>(){
            override fun onComplete() {}
            override fun onError(e: Throwable) {}
            override fun onNext(values: List<BankEntity>) {
                database.runInTransaction({
                    values.map { value-> value.paymentId = paymentId }
                    database.banks().insert(values)
                })
            }
        }))
    }
}