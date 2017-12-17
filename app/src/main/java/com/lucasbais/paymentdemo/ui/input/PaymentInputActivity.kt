package com.lucasbais.paymentdemo.ui.input

import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.isEnter
import com.lucasbais.paymentdemo.ui.BaseActivity
import com.lucasbais.paymentdemo.ui.creditcard.PaymentMethodActivity
import com.lucasbais.paymentdemo.viewmodel.PaymentContextViewModel
import kotlinx.android.synthetic.main.activity_payment_input.*
import javax.inject.Inject

class PaymentInputActivity : BaseActivity() {

    @Inject
    internal lateinit var paymentContext: PaymentContextViewModel

    companion object {

        private const val IMMERSIVE_ANIMATION_DELAY = 500L

        private const val NON_IMMERSIVE_ANIMATION_DELAY = 500L

    }

    private val onEnterListener = object : View.OnKeyListener {
        override fun onKey(p0: View?, key: Int, event: KeyEvent?): Boolean {
            if (isEnter(key, event!!)) {
                paymentContext.setPriceToPay(currencyInput.text.toString())
                PaymentMethodActivity.start(this@PaymentInputActivity)
                return true
            }
            return false
        }
    }

    private var isImmersiveMode: Boolean = true

    private val visibilityHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApplicationComponent().inject(this)
        initializeView()
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_payment_input
    }

    private fun initializeView() {
        content.setOnClickListener { toggle() }
        currencyInput.addTextChangedListener(CurrencyTextWatcher(currencyInput))
        currencyInput.setOnKeyListener(onEnterListener)
    }

    override fun onResume() {
        forceImmersive()
        super.onResume()
    }

    private fun forceImmersive() {
        isImmersiveMode = false
        toggle()
    }

    override fun onPause() {
        stopImmersiveRunnables()
        super.onPause()
    }

    private fun stopImmersiveRunnables() {
        visibilityHandler.removeCallbacks(nonImmersiveConfigurationRunnable)
        visibilityHandler.removeCallbacks(immersiveConfigurationRunnable)
    }

    private fun toggle() {
        if (isImmersiveMode) {
            configureImmersive()
        } else {
            configureNonImmersive()
        }
        isImmersiveMode = !isImmersiveMode
    }

    private fun configureNonImmersive() {
        visibilityHandler.removeCallbacks(nonImmersiveConfigurationRunnable)
        visibilityHandler.postDelayed(immersiveConfigurationRunnable, IMMERSIVE_ANIMATION_DELAY)
    }

    private fun configureImmersive() {
        visibilityHandler.removeCallbacks(immersiveConfigurationRunnable)
        visibilityHandler.postDelayed(nonImmersiveConfigurationRunnable, NON_IMMERSIVE_ANIMATION_DELAY)
    }

    /**
     * I don't like this way to handle the immersive but is one of android samples,
     * I just wanted to try this feature.
     */
    private val nonImmersiveConfigurationRunnable = Runnable {
        content.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }

    private val immersiveConfigurationRunnable = Runnable {
        content.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
}
