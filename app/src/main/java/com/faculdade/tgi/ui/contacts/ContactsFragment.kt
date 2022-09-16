package com.faculdade.tgi.ui.contacts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.faculdade.tgi.R
import com.faculdade.tgi.constants.Constants
import com.faculdade.tgi.databinding.FragmentContactsBinding
import com.faculdade.tgi.ui.contactsform.ContactFormActivity

class ContactsFragment : Fragment() {

    private var _binding: FragmentContactsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ContactsViewModel
    private val adapter = ContactsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this)[ContactsViewModel::class.java]
        _binding = FragmentContactsBinding.inflate(inflater, container, false)

        binding.recyclerContacts.layoutManager = LinearLayoutManager(context)
        binding.recyclerContacts.adapter = adapter

        val listener = object : OnContactsListener {
            override fun onChangeStatus(id: Int): Boolean {
                val newStatus = viewModel.changeStatus(id)
                val text = if (newStatus) resources.getString(R.string.contact_activaded) else resources.getString(R.string.contact_inactivaded)
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                return newStatus
            }

            override fun onEdit(id: Int) {
                val intent = Intent(context, ContactFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(Constants.CONTACT.ID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                viewModel.delete(id)
                viewModel.getAll()
            }

        }
        adapter.attachContactsListener(listener)

        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.contacts.observe(viewLifecycleOwner) {
            val has_contacts = adapter.updateContacts(it)
            binding.textNoContacts.isVisible = !has_contacts
        }
    }

}