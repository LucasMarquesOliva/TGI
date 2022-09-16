package com.faculdade.tgi.ui.contacts

import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.faculdade.tgi.R
import com.faculdade.tgi.databinding.RowContactsBinding
import com.faculdade.tgi.model.ContactModel
import com.faculdade.tgi.ui.contacts.OnContactsListener

class ContactsViewHolder(private val bind: RowContactsBinding, private val listener: OnContactsListener) : RecyclerView.ViewHolder(bind.root) {

    fun bind(contact: ContactModel) {

        bind.textName.text = contact.name
        bind.textCell.text = contact.cell
        bind.switchMain.isChecked = contact.main
        bind.switchMessage.isChecked = contact.message
        bind.switchLocalization.isChecked = contact.localization

        setColorContactStatus(contact.active)

        bind.imageStatus.setOnClickListener {
            val contactActivated = listener.onChangeStatus(contact.id)
            setColorContactStatus(contactActivated)
        }

        bind.imageEdit.setOnClickListener {
            listener.onEdit(contact.id)
        }

        bind.imageDelete.setOnClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Excluir contato")
                .setMessage("Tem certeza que deseja excluir este contato?")
                .setPositiveButton("Sim") { dialod, witch ->
                    listener.onDelete(contact.id)
                }
                .setNegativeButton("Não") { dialod, witch ->

                }
                .create()
                .show()
        }
    }

    private fun setColorContactStatus(active: Boolean) =
        if (active) bind.imageStatus.colorFilter = null else bind.imageStatus.setColorFilter(R.color.gray)
}