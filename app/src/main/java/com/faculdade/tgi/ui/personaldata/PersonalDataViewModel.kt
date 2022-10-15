package com.faculdade.tgi.ui.personaldata

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faculdade.tgi.model.PersonalDataModel
import com.faculdade.tgi.repository.personaldata.PersonalDataRepository

class PersonalDataViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PersonalDataRepository(application.applicationContext)

    private val _loadData = MutableLiveData<PersonalDataModel>()
    val loadData: LiveData<PersonalDataModel> = _loadData

    fun loadData() {
        _loadData.value = repository.get()
    }
}