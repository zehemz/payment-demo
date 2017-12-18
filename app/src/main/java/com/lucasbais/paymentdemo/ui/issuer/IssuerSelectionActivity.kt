package com.lucasbais.paymentdemo.ui.issuer


import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.datasource.database.entity.IssuerEntity
import com.lucasbais.paymentdemo.ui.BasePaymentActivity
import com.lucasbais.paymentdemo.ui.installment.InstallmentActivity
import kotlinx.android.synthetic.main.activity_payment_method.*

class IssuerSelectionActivity : BasePaymentActivity(), IssuerCallback {

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, IssuerSelectionActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bankAdapter = IssuerAdapter(this)
        recyclerView.adapter = bankAdapter
        paymentContext.getIssuers().observe(this,
                Observer<List<IssuerEntity>> { values ->
                    values?.let {
                        bankAdapter.setValues(values)
                    }
                })
    }

    override fun onClick(issuer: IssuerEntity) {
        paymentContext.issuerSelected(issuer)
        InstallmentActivity.start(this)
    }

    override fun getMessage(): Int {
        return R.string.issuer_message
    }
}
