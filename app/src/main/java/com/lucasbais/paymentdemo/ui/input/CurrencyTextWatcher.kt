package com.lucasbais.paymentdemo.ui.input

import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat

open class CurrencyTextWatcher(private val editText: EditText) : TextWatcher {

    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        p0.toString().toIntOrNull()?.let {
            val formatter = NumberFormat.getCurrencyInstance()
            formatter.maximumFractionDigits = 0
            val numberAsCurrency: String = formatter.format(it)
            editText.setText(numberAsCurrency)
            Selection.setSelection(editText.text, editText.text.length)
        }
    }
}