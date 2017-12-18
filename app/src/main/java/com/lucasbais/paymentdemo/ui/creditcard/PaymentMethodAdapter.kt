package com.lucasbais.paymentdemo.ui.creditcard

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.databinding.HolderPaymentMethodBinding
import com.lucasbais.paymentdemo.datasource.entity.PaymentMethodEntity


class PaymentMethodAdapter(private val paymentCallback: PaymentCallback) : RecyclerView.Adapter<PaymentMethodHolder>() {

    private var adapterValues: List<PaymentMethodEntity>? = null

    override fun onBindViewHolder(holder: PaymentMethodHolder, position: Int) {
        holder.binding.payment = adapterValues?.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (adapterValues == null) 0 else adapterValues!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentMethodHolder {
        val binding: HolderPaymentMethodBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.holder_payment_method,
                parent, false)
        binding.callback = paymentCallback
        return PaymentMethodHolder(binding)
    }

    fun setValues(values: List<PaymentMethodEntity>) {
        if (this.adapterValues == null) {
            this.adapterValues = values
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return if (adapterValues != null) adapterValues!!.size else 0
                }

                override fun getNewListSize(): Int {
                    return values.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val old = adapterValues?.get(oldItemPosition)
                    val newElement = values[newItemPosition]
                    return old?.id.equals(newElement.id)
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val old = adapterValues?.get(oldItemPosition)
                    val newElement = values[newItemPosition]
                    return (old?.id.equals(newElement.id))
                }
            })
            adapterValues = values
            diffResult.dispatchUpdatesTo(this)
        }
    }
}

