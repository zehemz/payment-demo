package com.lucasbais.paymentdemo.ui.bank


import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lucasbais.paymentdemo.datasource.database.entity.BankEntity
import com.lucasbais.paymentdemo.ui.BasePaymentActivity
import kotlinx.android.synthetic.main.activity_payment_method.*

class BankSelectionActivity : BasePaymentActivity(), BankCallback {

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, BankSelectionActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bankAdapter = BankAdapter(this)
        recyclerView.adapter = bankAdapter
        paymentContext.getBanks().observe(this,
                Observer<List<BankEntity>> { values ->
                    values?.let {
                        bankAdapter.setValues(values)
                    }
                })
    }

    override fun onClick(bank: BankEntity) {
        paymentContext.setBankSelected(bank)
    }
}
