package com.faculdade.tgi.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faculdade.tgi.databinding.RowContactsBinding
import com.faculdade.tgi.model.ContactModel

class ContactsAdapter : RecyclerView.Adapter<ContactsViewHolder>() {

    private var contactList: List<ContactModel> = listOf()
    private lateinit var listener: OnContactsListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val item = RowContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount(): Int {
        return contactList.count()
    }

    fun updateContacts(list: List<ContactModel>): Boolean {
        contactList = list
        notifyDataSetChanged()
        return contactList.isNotEmpty()
    }

    fun attachContactsListener(contactsListener: OnContactsListener) {
        listener = contactsListener
    }
}