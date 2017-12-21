package com.lucasbais.paymentdemo.ui.input

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import com.lucasbais.paymentdemo.R
import com.lucasbais.paymentdemo.utilities.isEnter
import com.lucasbais.paymentdemo.ui.BaseActivity
import com.lucasbais.paymentdemo.ui.creditcard.PaymentMethodActivity
import com.lucasbais.paymentdemo.viewmodel.PaymentContextViewModel
import kotlinx.android.synthetic.main.activity_payment_input.*
import javax.inject.Inject

class PaymentInputActivity : BaseActivity() {


    private lateinit var paymentContext: PaymentContextViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {

        private const val IMMERSIVE_ANIMATION_DELAY = 500L

        private const val NON_IMMERSIVE_ANIMATION_DELAY = 500L

        private const val EXTRA_SHOW_USER_RESULT = "EXTRA_SHOW_USER_RESULT"

        fun startShowingUserValues(activity: AppCompatActivity){
            val intent = Intent(activity, PaymentInputActivity::class.java)
            intent.putExtra(EXTRA_SHOW_USER_RESULT, true)
            intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP)
            activity.startActivity(intent)
        }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApplicationComponent().inject(this)
        paymentContext = ViewModelProviders.of(this, viewModelFactory)
                .get(PaymentContextViewModel::class.java)

        initializeView()
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_payment_input
    }

    override fun onNewIntent(intent: Intent?) {
        if(shouldShowDialog(intent)){
            showResultDialog()
        }
    }

    private fun initializeView() {
        content.setOnClickListener { toggle() }
        currencyInput.addTextChangedListener(CurrencyTextWatcher(currencyInput))
        currencyInput.setOnKeyListener(onEnterListener)
    }

    private fun showResultDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.user_input_alert_title)
        builder.setMessage(paymentContext.getCompleteUserInput())
        builder.show()
    }

    private fun shouldShowDialog(intent: Intent?) = (intent!= null
            && intent.extras !=null
            && intent.extras.containsKey(EXTRA_SHOW_USER_RESULT)
            && intent.getBooleanExtra(EXTRA_SHOW_USER_RESULT, false))

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
    private var isImmersiveMode: Boolean = true

    private val visibilityHandler = Handler()

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
