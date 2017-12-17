package com.lucasbais.paymentdemo.internal

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.lucasbais.paymentdemo.R

class ImageBindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["thumbnail"])
        fun loadImageUrl(view: ImageView, url: String) {
            GlideApp.with(view.context)
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .into(view)
        }
    }

}
