package com.lucasbais.paymentdemo.ui.creditcard


import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.datasource.entity.PaymentMethodEntity
import com.lucasbais.paymentdemo.ui.issuer.IssuerSelectionActivity
import com.lucasbais.paymentdemo.ui.BasePaymentActivity
import kotlinx.android.synthetic.main.activity_payment_method.*

class PaymentMethodActivity : BasePaymentActivity(), PaymentCallback {

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, PaymentMethodActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val paymentMethodAdapter = PaymentMethodAdapter(this)
        recyclerView.adapter = paymentMethodAdapter
        paymentContext.getCreditCardPaymentMethods().observe(this,
                Observer<List<PaymentMethodEntity>> { values ->
                    values?.let {
                        paymentMethodAdapter.setValues(values)
                    }
                })
    }

    override fun onClick(paymentMethodEntity: PaymentMethodEntity) {
        paymentContext.paymentMethodSelected(paymentMethodEntity)
        IssuerSelectionActivity.start(this)
    }

    override fun getMessage(): Int {
        return R.string.payment_message
    }
}
