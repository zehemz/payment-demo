package com.lucasbais.paymentdemo.ui


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.GridLayoutManager
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.viewmodel.PaymentContextViewModel
import kotlinx.android.synthetic.main.activity_payment_method.*
import javax.inject.Inject

abstract class BasePaymentActivity : BaseActivity() {

    internal lateinit var paymentContext: PaymentContextViewModel

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApplicationComponent().inject(this)
        paymentContext = ViewModelProviders.of(this, viewModelFactory).get(PaymentContextViewModel::class.java)
        recyclerView.layoutManager = GridLayoutManager(baseContext, 2, GridLayoutManager.VERTICAL, false)
        textMessage.text = getString(getMessage())
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_payment_method
    }

    @StringRes
    abstract fun getMessage(): Int
}
