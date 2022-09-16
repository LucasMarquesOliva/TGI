package com.faculdade.tgi.ui.home

import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.faculdade.tgi.databinding.RowHomeBinding
import com.faculdade.tgi.model.ContactModel

class HomeViewHolder(private val bind: RowHomeBinding, private val listener: OnHomeListener) : RecyclerView.ViewHolder(bind.root) {

    fun bind(contact: ContactModel) {

        bind.textName.text = contact.name
        bind.textCell.text = contact.cell
        bind.imageStar.isVisible = contact.main
        bind.switchMessage.isChecked = contact.message
        bind.switchLocalization.isChecked = contact.localization

        bind.layoutContact.setOnClickListener {
            listener.onClick(contact.id)
        }

        bind.layoutContact.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle("Alteração de contato")
                .setMessage("O que deseja fazer?")
                .setPositiveButton("Editar") { dialod, witch ->
                    listener.onEdit(contact.id)
                }
                .setNegativeButton("Excluir") { dialod, witch ->
                    listener.onDelete(contact.id)
                }
                .create()
                .show()
            true
        }
    }

}