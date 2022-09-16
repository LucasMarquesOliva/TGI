package com.faculdade.tgi.ui.contactsform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faculdade.tgi.model.ContactModel
import com.faculdade.tgi.repository.contact.ContactRepository

class ContactFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ContactRepository(application)

    private val _contact = MutableLiveData<ContactModel>()
    val contact: LiveData<ContactModel> = _contact

    private val _saveContact = MutableLiveData<String>()
    val saveContact: LiveData<String> = _saveContact

    fun save(contact: ContactModel) {
        var mainContact = repository.getMain()

        if (contact.main) {
            clearOldMain(mainContact)
        } else {
            if (mainContact == null) {
                contact.main = true
            }
        }
        upsertContact(contact)
    }

    fun get(id: Int) {
        _contact.value = repository.get(id)
    }

    fun checkData(name: String, cel: String): String {
        return if (name == "" || cel == "") {
            "Preencher campos obrigatórios"
        } else if (cel.length < 11) {
            "Preencher celular corretamente"
        } else {
            ""
        }
    }

    private fun clearOldMain(contact: ContactModel) {
        if (contact != null) {
            contact.main = false
            repository.update(contact)
        }
    }

    private fun upsertContact(contact: ContactModel) {
        val name = contact.name
        if (contact.id == 0) {
            if (repository.insert(contact)) {
                _saveContact.value = "Contato $name inserido com sucesso"
            } else {
                _saveContact.value = "Não foi possível inserir o contato $name"
            }
        } else {
            if (repository.update(contact)) {
                _saveContact.value = "Contato $name atualizado com sucesso"
            } else {
                _saveContact.value = "Não foi possível atualizar o contato $name"
            }
        }
    }

}