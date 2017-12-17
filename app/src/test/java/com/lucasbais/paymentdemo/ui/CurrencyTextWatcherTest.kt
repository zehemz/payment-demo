package com.lucasbais.paymentdemo.ui

import android.widget.EditText
import com.lucasbais.paymentdemo.ui.input.CurrencyTextWatcher
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CurrencyTextWatcherTest {
    @Mock
    lateinit var mockEditText:EditText

    lateinit var currencyTextWatcher: CurrencyTextWatcher

    @Before
    fun setUp(){
        currencyTextWatcher = CurrencyTextWatcher(mockEditText)
    }

//    @Test
//    fun onTextChanged_empty_string() {
//        currencyTextWatcher.onTextChanged("")
//    }
}