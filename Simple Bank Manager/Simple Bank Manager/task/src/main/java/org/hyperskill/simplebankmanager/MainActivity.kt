package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.navigation.findNavController

private const val DEFAULT_BALANCE = 100.0
private const val DEFAULT_USERNAME = "Lara"
private const val DEFAULT_PASSWORD = "1234"
private val defaultCurrencyMap = mapOf(
    "EUR" to mapOf(
        "GBP" to 0.5,
        "USD" to 2.0
    ),
    "GBP" to mapOf(
        "EUR" to 2.0,
        "USD" to 4.0
    ),
    "USD" to mapOf(
        "EUR" to 0.5,
        "GBP" to 0.25
    )
)
private val defaultBillInfoMap = mapOf(
    "ELEC" to Triple("Electricity", "ELEC", 45.0),
    "GAS" to Triple("Gas", "GAS", 20.0),
    "WTR" to Triple("Water", "WTR", 25.5)
)

class MainActivity : AppCompatActivity() {
    private val currentBalanceSharedViewModel: CurrentBalanceSharedViewModel by viewModels()
    private val loginSharedViewModel: LoginViewModel by viewModels()
    private val exchangeSharedViewModel: CurrencyExchangeSharedViewModel by viewModels()
    private val billInfoSharedViewModel: BillInfoSharedViewModel by viewModels()

    //creating instance of OnBackPressedCallback to handle back button
    //since onBackPressed is deprecate
    val callback = object: OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            //pop current fragment from back stack
            findNavController(R.id.nav_host_fragment).popBackStack()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //login info from intent, update
        val userName = intent?.getStringExtra(getString(R.string.username)) ?: DEFAULT_USERNAME
        val password = intent?.getStringExtra(getString(R.string.password)) ?: DEFAULT_PASSWORD
        loginSharedViewModel.updateUsername(userName)
        loginSharedViewModel.updatePassword(password)

        //balance info from intent, update
        val balanceValue = intent?.getDoubleExtra(getString(R.string.balance_key), DEFAULT_BALANCE)
            ?: DEFAULT_BALANCE
        currentBalanceSharedViewModel.updateBalance(balanceValue)

        //currency exchange info from intent, update
        var exchangeMap = intent?.getSerializableExtra(getString(R.string.exchange_intent_map)) as?
                Map<String, Map<String, Double>> ?: defaultCurrencyMap
        exchangeSharedViewModel.updateExchangeMap(exchangeMap)

        //bill info from intent, update
        var billInfoMap = intent?.getSerializableExtra(getString(R.string.pay_bill_intent_map)) as?
                Map<String, Triple<String, String, Double>> ?: defaultBillInfoMap
        billInfoSharedViewModel.updateBillInfo(billInfoMap)
    }
}