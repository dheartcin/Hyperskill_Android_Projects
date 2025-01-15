package org.hyperskill.simplebankmanager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentBalanceSharedViewModel: ViewModel() {
    //scope of this shared viewModel is activity
    var balance = MutableLiveData<Double>()
    fun updateBalance(newBalance:Double) {
        balance.value = newBalance
    }
}