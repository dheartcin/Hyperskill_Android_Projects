package org.hyperskill.simplebankmanager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BillInfoSharedViewModel: ViewModel() {
    val billInfo = MutableLiveData<Map<String, Triple<String, String, Double>>>()
    fun updateBillInfo(newBill: Map<String, Triple<String, String, Double>>){
        billInfo.value = newBill
    }
}