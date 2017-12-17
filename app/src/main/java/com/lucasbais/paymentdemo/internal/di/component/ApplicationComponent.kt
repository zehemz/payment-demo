package com.lucasbais.paymentdemo.internal.di.component


import com.lucasbais.paymentdemo.AndroidApplication
import com.lucasbais.paymentdemo.internal.di.module.ApplicationModule
import com.lucasbais.paymentdemo.ui.BasePaymentActivity
import com.lucasbais.paymentdemo.ui.input.PaymentInputActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    fun inject(androidApplication: AndroidApplication)
    fun inject(paymentInputActivity: PaymentInputActivity)
    fun inject(basePaymentActivity: BasePaymentActivity)

}
