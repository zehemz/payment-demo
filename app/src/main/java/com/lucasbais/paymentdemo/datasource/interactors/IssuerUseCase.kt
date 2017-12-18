package com.lucasbais.paymentdemo.datasource.interactors


import com.lucasbais.paymentdemo.datasource.entity.IssuerEntity
import com.lucasbais.paymentdemo.datasource.network.PaymentClient
import io.reactivex.Observable
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Named


class IssuerUseCase @Inject internal constructor(private val paymentService: PaymentClient,
                                                 @Named("netExecutor") threadExecutor: Executor,
                                                 @Named("diskExecutor") postExecutionThread: Executor)
    : UseCase<List<IssuerEntity>, IssuerUseCase.Params>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Params): Observable<List<IssuerEntity>> {
        return paymentService.getIssuers(params.paymentMethodId)
    }

    class Params private constructor(internal val paymentMethodId: String) {
        companion object {
            fun create(paymentMethodId: String): Params {
                return Params(paymentMethodId)
            }
        }
    }
}