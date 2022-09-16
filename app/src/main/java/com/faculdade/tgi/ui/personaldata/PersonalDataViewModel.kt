package com.faculdade.tgi.ui.personaldata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PersonalDataViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is personal data Fragment"
    }
    val text: LiveData<String> = _text
}