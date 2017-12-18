package com.lucasbais.paymentdemo.utilities

import android.view.KeyEvent
import android.view.View

/**
 * Este archivo está generado solamente para probar extension functions de kotlin,
 * no genera ningún tipo de uso real.
 */
internal fun View.OnKeyListener.isEnter(keyCode: Int, event: KeyEvent): Boolean {
    return (event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)
}