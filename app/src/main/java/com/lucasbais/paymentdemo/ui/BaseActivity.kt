package com.lucasbais.paymentdemo.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.lucasbais.paymentdemo.AndroidApplication
import com.lucasbais.paymentdemo.internal.di.component.ApplicationComponent


@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
    }

    fun getApplicationComponent(): ApplicationComponent {
        return (baseContext.applicationContext as AndroidApplication).applicationComponent
    }
}