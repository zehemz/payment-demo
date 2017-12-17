package com.lucasbais.paymentdemo.internal.interactors

import com.lucasbais.paymentdemo.datasource.database.entity.PaymentMethodEntity
import com.lucasbais.paymentdemo.datasource.network.PaymentClient
import io.reactivex.Observable
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Named


class PaymentOptionsUseCase @Inject internal constructor(private val paymentService: PaymentClient,
                                                         @Named("netExecutor") threadExecutor: Executor,
                                                         @Named("diskExecutor") postExecutionThread: Executor)
    : UseCase<List<PaymentMethodEntity>, ParamsEmpty>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: ParamsEmpty): Observable<List<PaymentMethodEntity>> {
        return paymentService.getPaymentMethods()
    }
}