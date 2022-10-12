package com.faculdade.tgi.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.faculdade.tgi.R
import com.faculdade.tgi.databinding.ActivityUserBinding
import com.faculdade.tgi.constants.Constants
import com.faculdade.tgi.infra.SecurityPreferences
import com.faculdade.tgi.ui.main.MainActivity
import com.faculdade.tgi.ui.personaldataform.PersonalDataFormActivity

class UserActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]

        supportActionBar?.hide()

        binding.buttonSave.setOnClickListener(this)

        verifyUserName()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_save) {
            handleSave()
        }
    }

    private fun verifyUserName() {
        if (viewModel.checkUser()) {
            SecurityPreferences(this).storeBoolean(Constants.KEY.FIRST_ACCESS, false)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun handleSave() {
        val name = binding.editName.text.toString()
        if (name != "") {
            viewModel.saveName(name)
            SecurityPreferences(this).storeBoolean(Constants.KEY.FIRST_ACCESS, true)
            startActivity(Intent(this, PersonalDataFormActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, R.string.validation_user_name, Toast.LENGTH_SHORT).show()
        }
    }
}