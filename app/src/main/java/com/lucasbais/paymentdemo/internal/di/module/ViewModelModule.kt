package com.lucasbais.paymentdemo.internal.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.lucasbais.paymentdemo.datasource.PaymentDataService
import com.lucasbais.paymentdemo.datasource.PaymentService
import com.lucasbais.paymentdemo.viewmodel.PaymentContextViewModel
import com.lucasbais.paymentdemo.viewmodel.PaymentViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun paymentService(paymentDataService: PaymentDataService): PaymentService

    @Binds
    @IntoMap
    @ViewModelKey(PaymentContextViewModel::class)
    internal abstract fun bindPaymentContextViewModel(paymentViewModel: PaymentContextViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: PaymentViewModelFactory): ViewModelProvider.Factory
}