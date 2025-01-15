package org.hyperskill.simplebankmanager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.Serializable

class CurrencyExchangeSharedViewModel: ViewModel() {
    var exchangeMap = MutableLiveData<Map<String, Map<String, Double>>>()
    fun updateExchangeMap(newMap: Map<String, Map<String, Double>>){
        exchangeMap.value = newMap
    }
}