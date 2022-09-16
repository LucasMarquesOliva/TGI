package com.faculdade.tgi.ui.contacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faculdade.tgi.model.ContactModel
import com.faculdade.tgi.repository.contact.ContactRepository

class ContactsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ContactRepository(application.applicationContext)

    private val listContacts = MutableLiveData<List<ContactModel>>()
    val contacts: LiveData<List<ContactModel>> = listContacts

    fun getAll() {
        listContacts.value = repository.getAll()
    }

    fun changeStatus(id: Int): Boolean {
        var contact = repository.get(id)
        val newStatus = !contact.active

        contact.active = newStatus
        repository.update(contact)
        return newStatus
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
}