package com.faculdade.tgi.repository.contact

import android.content.Context
import com.faculdade.tgi.model.ContactModel
import com.faculdade.tgi.repository.ProjectDataBase

class ContactRepository(context: Context) {

    private val contactDataBase = ProjectDataBase.getDataBase(context).contactDAO()

    fun insert(contact: ContactModel): Boolean {
        return contactDataBase.insert(contact) > 0
    }

    fun update(contact: ContactModel): Boolean {
        return contactDataBase.update(contact) > 0
    }

    fun delete(id: Int) {
        contactDataBase.delete(get(id))
    }

    fun get(id: Int): ContactModel {
        return contactDataBase.get(id)
    }

    fun getMain(): ContactModel {
        return contactDataBase.getMain()
    }

    fun getActive(): List<ContactModel> {
        return contactDataBase.getActive()
    }

    fun getAll(): List<ContactModel> {
        return contactDataBase.getAll()
    }

}