package com.faculdade.tgi.ui.personaldataform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faculdade.tgi.model.PersonalDataModel
import com.faculdade.tgi.repository.personaldata.PersonalDataRepository
import java.math.RoundingMode
import java.text.DecimalFormat

class PersonalDataFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PersonalDataRepository(application)

    private val _person = MutableLiveData<PersonalDataModel>()
    val person: LiveData<PersonalDataModel> = _person

    fun save(person: PersonalDataModel) {
        repository.update(person)
    }

    fun loadData() {
        _person.value = repository.get()
    }

    fun calculateBMI(weight: Double, height: Double): String {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        return df.format((weight / height / height) * 10000).toString().replace(",", ".")
    }
}