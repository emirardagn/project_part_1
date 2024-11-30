package com.example.novnavex.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel:ViewModel() {
    val userName = MutableLiveData<String>()
}