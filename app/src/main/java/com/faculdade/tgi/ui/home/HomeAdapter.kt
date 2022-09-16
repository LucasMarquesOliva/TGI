package com.faculdade.tgi.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faculdade.tgi.databinding.RowHomeBinding
import com.faculdade.tgi.model.ContactModel

class HomeAdapter : RecyclerView.Adapter<HomeViewHolder>() {

    private var contactList: List<ContactModel> = listOf()
    private lateinit var listener: OnHomeListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val item = RowHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(contactList[position])
    }

    override fun getItemCount(): Int {
        return contactList.count()
    }

    fun updateContacts(list: List<ContactModel>): Boolean {
        contactList = list.sortedByDescending { it.main }
        notifyDataSetChanged()
        return contactList.isNotEmpty()
    }

    fun attachHomeListener(homeListener: OnHomeListener) {
        listener = homeListener
    }
}