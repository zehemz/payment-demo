package com.lucasbais.paymentdemo

import android.app.Application

import com.lucasbais.paymentdemo.internal.di.component.ApplicationComponent
import com.lucasbais.paymentdemo.internal.di.component.DaggerApplicationComponent
import com.lucasbais.paymentdemo.internal.di.module.ApplicationModule
import javax.inject.Inject

class AndroidApplication : Application() {

    @Inject
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        this.initializeApplicationComponent()
    }

    private fun initializeApplicationComponent() {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()
                .inject(this)
    }
}
