package com.faculdade.tgi.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.faculdade.tgi.model.PersonalDataModel
import com.faculdade.tgi.repository.personaldata.PersonalDataRepository

class UserViewModel(application: Application) : AndroidViewModel(application)  {

    private val repository = PersonalDataRepository(application.applicationContext)

    fun checkUser(): Boolean {
        //Verifica se já existe o usuário na tabela
        val teste = repository.get()
        val teste2 = repository.get() != null
        val teste3 = repository.get() == null


        return repository.get() != null
    }

    fun saveName(name: String) {
        //Salva apenas o nome na tabela
        repository.insert(PersonalDataModel().apply{ this.name = name })
    }

}