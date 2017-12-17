package com.lucasbais.paymentdemo

import android.view.KeyEvent
import android.view.View

/**
 * Just testing features of kotlin, fancy way to do things and not really the best way to do it.
 */
internal fun View.OnKeyListener.isEnter(keyCode: Int, event: KeyEvent): Boolean {
    return (event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)
}