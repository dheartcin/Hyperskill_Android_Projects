package org.hyperskill.simplebankmanager

import android.content.Intent
import androidx.lifecycle.ViewModel
import android.os.Bundle
import androidx.lifecycle.MutableLiveData

class LoginViewModel: ViewModel() {
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun updateUsername(newUsername: String) {
        username.value = newUsername
    }
    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }
}