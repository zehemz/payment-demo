package com.lucasbais.paymentdemo.ui.installment


import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.datasource.database.entity.PayerCostEntity
import com.lucasbais.paymentdemo.ui.BasePaymentActivity
import com.lucasbais.paymentdemo.ui.input.PaymentInputActivity
import kotlinx.android.synthetic.main.activity_payment_method.*

class InstallmentActivity : BasePaymentActivity(), PayerCostCallback {

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, InstallmentActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val payerCostAdapter = PayerCostAdapter(this)
        recyclerView.adapter = payerCostAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        paymentContext.getPayerCosts().observe(this,
                Observer<List<PayerCostEntity>> { values ->
                    values?.let {
                        payerCostAdapter.setValues(values)
                    }
                })
    }

    override fun getMessage(): Int {
        return R.string.installment_message
    }

    override fun onClick(payerCost: PayerCostEntity) {
        paymentContext.payerCostSelected(payerCost)
        PaymentInputActivity.startShowingUserValues(this)
    }
}
