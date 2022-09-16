package com.faculdade.tgi.ui.home

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.faculdade.tgi.model.ContactModel
import com.faculdade.tgi.repository.contact.ContactRepository
import com.google.android.gms.location.FusedLocationProviderClient


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ContactRepository(application.applicationContext)

    private val listContacts = MutableLiveData<List<ContactModel>>()
    val contacts: LiveData<List<ContactModel>> = listContacts

    fun getActive() {
        listContacts.value = repository.getActive()
    }

    fun delete(id: Int) {
        repository.delete(id)
    }

    fun sendSMS(id: Int): String {
        val contact = repository.get(id)
        return if (contact.message) {
            val name = contact.name
            val cell = contact.cell
            val message = if (contact.messagetext != "") contact.messagetext else "$name, estou precisando de ajuda!"

            try {
                SmsManager.getDefault().sendTextMessage("55$cell", null, message, null, null)
                "SMS enviado para $name"
            } catch (ex: Exception) {
                "Erro ao enviar SMS para $name... " + ex.message
            }
        } else {
            ""
        }
    }

    fun sendLocalization(id: Int, fusedLocationProviderClient: FusedLocationProviderClient, hasLocationPermission: Boolean): String {
        val contact = repository.get(id)
        return if (contact.localization) {
            val name = contact.name
            val cell = contact.cell
            var message = "Link da localização: "

            if (hasLocationPermission) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        var latitude = it.latitude
                        var longitude = it.longitude
                        message += "http://maps.google.com/?q=$latitude,$longitude"
                        try {
                            SmsManager.getDefault().sendTextMessage("55$cell", null, message, null, null)
                        } catch (ex: Exception) {
                        }
                    }
                }
                "Localização enviada para $name"
            } else {
                "Sem permissão para localização"
            }
        } else {
            ""
        }
    }

    fun getSMSIntent(id: Int): Intent? {
        val contact = repository.get(id)
        var intent: Intent? = null
        if (contact.message) {
            val phone = contact.cell
            val name = contact.name
            val message = if (contact.messagetext != "") contact.messagetext else "$name, estou precisando de ajuda!"

            intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$phone"))
            intent.putExtra("sms_body", message)
        }
        return intent
    }

    fun getWPPIntent(id: Int): Intent? {
        val contact = repository.get(id)
        var intent: Intent? = null
        if (contact.message) {
            val phone = contact.cell
            val name = contact.name
            val message = if (contact.messagetext != "") contact.messagetext else "$name, estou precisando de ajuda!"

            intent = Intent("android.intent.action.MAIN")
            intent.putExtra("jid", "55$phone@s.whatsapp.net")
            intent.putExtra(Intent.EXTRA_TEXT, message)
            intent.setAction(Intent.ACTION_SEND)
            intent.setPackage("com.whatsapp")
            intent.setType("text/plain")
        }
        return intent
    }
}