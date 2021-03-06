package com.lucasbais.paymentdemo.ui.issuer

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.databinding.HolderIssuerBinding
import com.lucasbais.paymentdemo.datasource.entity.IssuerEntity

//TODO :: refactor make generic adapter with callback BaseAdapter<T, V>
class IssuerAdapter(private val issuerCallback: IssuerCallback) : RecyclerView.Adapter<IssuerHolder>() {

    private var adapterValues: List<IssuerEntity>? = null

    override fun onBindViewHolder(holder: IssuerHolder, position: Int) {
        holder.binding.issuer = adapterValues?.get(position)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (adapterValues == null) 0 else adapterValues!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuerHolder {
        val binding: HolderIssuerBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.holder_issuer,
                parent, false)
        binding.callback = issuerCallback
        return IssuerHolder(binding)
    }

    fun setValues(values: List<IssuerEntity>) {
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

