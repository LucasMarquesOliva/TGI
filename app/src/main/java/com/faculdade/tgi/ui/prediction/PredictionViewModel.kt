package com.faculdade.tgi.ui.prediction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faculdade.tgi.model.ContactModel
import com.faculdade.tgi.model.PersonalDataModel
import com.faculdade.tgi.repository.personaldata.PersonalDataRepository

class PredictionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PersonalDataRepository(application.applicationContext)

    private val _loadData = MutableLiveData<PersonalDataModel>()
    val loadData: LiveData<PersonalDataModel> = _loadData

    fun loadData() {
        _loadData.value = repository.get()
    }
}