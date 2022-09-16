package com.faculdade.tgi.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faculdade.tgi.constants.Constants
import com.faculdade.tgi.infra.SecurityPreferences
import com.faculdade.tgi.repository.personaldata.PersonalDataRepository

class MainViewModel(application: Application) : AndroidViewModel(application)  {

    private val repository = PersonalDataRepository(application.applicationContext)

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    fun loadUserName() {
        _name.value = repository.get().name
    }

}