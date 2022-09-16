package com.faculdade.tgi.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.faculdade.tgi.R
import com.faculdade.tgi.constants.Constants
import com.faculdade.tgi.databinding.FragmentHomeBinding
import com.faculdade.tgi.ui.contactsform.ContactFormActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class HomeFragment : Fragment(), View.OnClickListener {

    companion object {
        const val PERMISSION_LOCATION_REQUEST_CODE = 1
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel
    private val adapter = HomeAdapter()

    private lateinit var soundAlert: MediaPlayer
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.buttonSoundAlert.setOnClickListener(this)

        binding.recyclerContactsHome.layoutManager = LinearLayoutManager(context)
        binding.recyclerContactsHome.adapter = adapter

        //late = MediaPlayer.create(context, R.raw.latindo)
        soundAlert = MediaPlayer.create(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        val listener = object : OnHomeListener {
            override fun onClick(id: Int) {
                sendAlert(id)
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
                viewModel.getActive()
            }

        }
        adapter.attachHomeListener(listener)

        observe()

        return binding.root
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_sound_alert) {
            if (!soundAlert.isPlaying) {
                soundAlert.start()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getActive()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.contacts.observe(viewLifecycleOwner) {
            val has_contacts = adapter.updateContacts(it)
            binding.textNoContactsHome.isVisible = !has_contacts
        }
    }

    private fun sendAlert(id: Int) {
        //Envia SMS
        val actionSMS = viewModel.sendSMS(id)
        if (actionSMS != "") {
            Toast.makeText(context, actionSMS, Toast.LENGTH_SHORT).show()
        }

        //Envia Localização
        val actionLocalization = viewModel.sendLocalization(id, fusedLocationProviderClient, true)
        if (actionLocalization != "") {
            Toast.makeText(context, actionLocalization, Toast.LENGTH_SHORT).show()
        }

        //Se não houve nenhuma mensagem de retorno, não houve ação
        if (actionSMS == "" && actionLocalization == "") {
            Toast.makeText(context, "Não há ações a tomar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hasLocationPermission(): Boolean {
        return (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION) } != PackageManager.PERMISSION_GRANTED &&
                context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) } != PackageManager.PERMISSION_GRANTED)
    }
}

