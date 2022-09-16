package com.faculdade.tgi.ui.contacts

interface OnContactsListener {
    fun onChangeStatus(id: Int): Boolean
    fun onEdit(id: Int)
    fun onDelete(id: Int)
}