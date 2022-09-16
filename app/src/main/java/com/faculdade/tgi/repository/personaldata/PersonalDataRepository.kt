package com.faculdade.tgi.repository.personaldata

import android.content.Context
import com.faculdade.tgi.model.PersonalDataModel
import com.faculdade.tgi.repository.ProjectDataBase

class PersonalDataRepository(context: Context) {

    private val personalDataDataBase = ProjectDataBase.getDataBase(context).personalDataDAO()

    fun insert(person: PersonalDataModel): Boolean {
        return personalDataDataBase.insert(person) > 0
    }

    fun update(person: PersonalDataModel): Boolean {
        return personalDataDataBase.update(person) > 0
    }

    fun delete(id: Int) {
        personalDataDataBase.delete(get(id))
    }

    fun get(id: Int = 1): PersonalDataModel {
        return personalDataDataBase.get(id)
    }

}