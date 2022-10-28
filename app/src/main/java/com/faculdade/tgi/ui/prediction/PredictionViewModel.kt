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

    fun updateResult(outcome: Int) {
        val person = repository.get()
        person.outcome = outcome
        repository.update(person)
    }

    fun getDiabetesPedigreeValueById(id: Int): Double {
        return when(id) {
            0 -> 0.004
            1 -> 0.04
            2 -> 0.06
            3 -> 0.06
            4 -> 0.3
            5 -> 0.3
            6 -> 0.75
            else -> 0.00
        }
    }
}