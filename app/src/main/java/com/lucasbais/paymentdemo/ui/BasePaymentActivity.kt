package com.lucasbais.paymentdemo.ui


import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.widget.GridLayoutManager
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.viewmodel.PaymentContextViewModel
import kotlinx.android.synthetic.main.activity_payment_method.*
import javax.inject.Inject

abstract class BasePaymentActivity : BaseActivity() {

    @Inject
    internal lateinit var paymentContext: PaymentContextViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView.layoutManager = GridLayoutManager(baseContext, 2, GridLayoutManager.VERTICAL, false)
        textMessage.text = getString(getMessage())
        getApplicationComponent().inject(this)
        lifecycle.addObserver(paymentContext)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_payment_method
    }

    @StringRes
    abstract fun getMessage(): Int
}
