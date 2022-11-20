package com.faculdade.tgi.ui.contactsform

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.faculdade.tgi.R
import com.faculdade.tgi.constants.Constants
import com.faculdade.tgi.databinding.ActivityContactFormBinding
import com.faculdade.tgi.model.ContactModel

class ContactFormActivity : AppCompatActivity(), View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivityContactFormBinding
    private lateinit var viewModel: ContactFormViewModel

    private var contactId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ContactFormViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.buttonSave.setOnClickListener(this)
        binding.switchMessage.setOnCheckedChangeListener(this)
        binding.switchLocalization.setOnCheckedChangeListener(this)

        observe()
        loadData()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            val errorText = viewModel.checkData(binding.editName.text.toString(), binding.editDdd.text.toString(), binding.editCell.text.toString())
            if (errorText == "") {
                val model = ContactModel().apply {
                    this.id = contactId
                    this.name = binding.editName.text.toString()
                    this.cell = binding.editDdd.text.toString() + binding.editCell.text.toString()
                    this.main = binding.switchMain.isChecked
                    this.active = true
                    this.localization = binding.switchLocalization.isChecked
                    this.message = binding.switchMessage.isChecked
                    this.messagetext = binding.editMessageText.text.toString()
                }
                viewModel.save(model)
            } else {
                Toast.makeText(applicationContext, errorText, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (buttonView.id == R.id.switch_message) {
            //Solicita permissão para SMS
            checkPermissions(Constants.PERMISSION.SMS)
            //Exibe a caixa de mensagem opicional
            binding.editMessageText.visibility = if (isChecked) View.VISIBLE else View.INVISIBLE
        } else if (buttonView.id == R.id.switch_localization) {
            //Solicita permissão para GPS
            checkPermissions(Constants.PERMISSION.GPS)
        }
    }

    private fun observe() {
        viewModel.contact.observe(this, Observer {
            binding.editName.setText(it.name)
            binding.editDdd.setText(it.cell.substring(0, 2))
            binding.editCell.setText(it.cell.substring(2))
            binding.switchMain.isChecked = it.main
            binding.switchLocalization.isChecked = it.localization
            binding.switchMessage.isChecked = it.message
            binding.editMessageText.setText(it.messagetext)
        })
        viewModel.saveContact.observe(this, Observer {
            if (it != "") {
                Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            contactId = bundle.getInt(Constants.CONTACT.ID)
            viewModel.get(contactId)
        }
    }

    private fun checkPermissions(permissionType: String) {
        if (permissionType == Constants.PERMISSION.SMS) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), 101)
            }
        } else if (permissionType == Constants.PERMISSION.GPS) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION),
                    202
                )
            }
        }

    }

}