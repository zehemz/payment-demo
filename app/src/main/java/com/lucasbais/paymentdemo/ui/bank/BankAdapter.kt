package com.lucasbais.paymentdemo.ui.bank

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.databinding.HolderBankBinding
import com.lucasbais.paymentdemo.datasource.database.entity.BankEntity

//TODO :: refactor make generic adapter with callback BaseAdapter<T, V>
class BankAdapter(private val bankCallback: BankCallback) : RecyclerView.Adapter<BankHolder>() {

    private var adapterValues: List<BankEntity>? = null

    override fun onBindViewHolder(holder: BankHolder, position: Int) {
        holder.binding.bank = adapterValues?.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (adapterValues == null) 0 else adapterValues!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankHolder {
        val binding: HolderBankBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.holder_bank,
                parent, false)
        binding.callback = bankCallback
        return BankHolder(binding)
    }

    fun setValues(values: List<BankEntity>) {
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

