package com.lucasbais.paymentdemo.internal.interactors


import com.lucasbais.paymentdemo.datasource.database.entity.InstallmentEntity
import com.lucasbais.paymentdemo.datasource.network.PaymentClient
import io.reactivex.Observable
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Named


class InstallmentUseCase @Inject internal constructor(private val paymentService: PaymentClient,
                                                      @Named("netExecutor") threadExecutor: Executor,
                                                      @Named("mainExecutor") postExecutionThread: Executor)
    : UseCase<InstallmentEntity, InstallmentUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params): Observable<InstallmentEntity> {
        //TODO: Work around, el endpoint pidiendo por id devuelve una lista de installments
        return paymentService.getInstallments(params.paymentMethodId, params.issuerId, params.amount).map { list ->
            list[0]
        }
    }

    class Params private constructor(internal val paymentMethodId: String,
                                     internal val issuerId: String,
                                     internal val amount: Double) {

        companion object {
            fun create(paymentMethodId: String,
                       issuerId: String,
                       amount: Double): Params {
                return Params(paymentMethodId, issuerId, amount)
            }
        }
    }
}