package com.lucasbais.paymentdemo.ui.installment

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.databinding.HolderInstallmentBinding
import com.lucasbais.paymentdemo.datasource.database.entity.PayerCostEntity

//TODO :: refactor make generic adapter with callback BaseAdapter<T, V>
class PayerCostAdapter(private val callback: PayerCostCallback) : RecyclerView.Adapter<PayerCostHolder>() {

    private var adapterValues: List<PayerCostEntity>? = null

    override fun onBindViewHolder(holder: PayerCostHolder, position: Int) {
        holder.binding.payerCost = adapterValues?.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (adapterValues == null) 0 else adapterValues!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayerCostHolder {
        val binding: HolderInstallmentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.holder_installment,
                parent, false)
        binding.callback = callback
        return PayerCostHolder(binding)
    }

    fun setValues(values: List<PayerCostEntity>) {
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
                    return old?.recommendedMessage.equals(newElement.recommendedMessage)
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val old = adapterValues?.get(oldItemPosition)
                    val newElement = values[newItemPosition]
                    return (old?.recommendedMessage.equals(newElement.recommendedMessage))
                }
            })
            adapterValues = values
            diffResult.dispatchUpdatesTo(this)
        }
    }
}

