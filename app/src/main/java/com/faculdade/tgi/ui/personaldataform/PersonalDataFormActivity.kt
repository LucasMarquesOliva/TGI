package com.faculdade.tgi.ui.personaldataform

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.faculdade.tgi.R
import com.faculdade.tgi.constants.Constants
import com.faculdade.tgi.databinding.ActivityPersonalDataFormBinding
import com.faculdade.tgi.infra.SecurityPreferences
import com.faculdade.tgi.model.PersonalDataModel
import com.faculdade.tgi.ui.main.MainActivity
import com.faculdade.tgi.validations.DecimalDigitsInputFilter
import java.util.regex.PatternSyntaxException

class PersonalDataFormActivity : AppCompatActivity(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    private lateinit var binding: ActivityPersonalDataFormBinding
    private lateinit var viewModel: PersonalDataFormViewModel
    private var isFirstAccess: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PersonalDataFormViewModel::class.java]

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.buttonSave.setOnClickListener(this)
        binding.buttonLater.setOnClickListener(this)
        binding.radioFemale.setOnCheckedChangeListener(this)

        //Controla a quantidade de dígitos dos campos Double
        binding.editWeight.inputFilterDecimal(5, 1)
        binding.editHeight.inputFilterDecimal(5, 1)
        binding.editGlucose.inputFilterDecimal(5, 1)
        binding.editInsulin.inputFilterDecimal(5, 1)
        binding.editBloodPressure.inputFilterDecimal(5, 1)
        binding.editSkinThickness.inputFilterDecimal(5, 1)
        //binding.editDiabetesPedigree.inputFilterDecimal(6, 3)

        observe()
        checkFirstAccess()
        loadData()
        setBMICalculatorListener()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_save -> {
                val model = PersonalDataModel().apply {
                    this.id = 1
                    this.name = binding.editName.text.toString()
                    this.age = binding.editAge.text.toString().toInt()
                    this.gender = if (binding.radioMale.isChecked) 0 else 1
                    this.weight = binding.editWeight.text.toString().toDouble()
                    this.height = binding.editHeight.text.toString().toDouble()
                    this.bmi = binding.editBmi.text.toString().toDouble()
                    this.glucose = binding.editGlucose.text.toString().toDouble()
                    this.insulin = binding.editInsulin.text.toString().toDouble()
                    this.bloodPressure = binding.editBloodPressure.text.toString().toDouble()
                    this.skinThickness = binding.editSkinThickness.text.toString().toDouble()
                    this.familyHistory = binding.spinnerFamilyHistory.selectedItemId.toInt()
                    this.pregnancies = if (binding.radioFemale.isChecked) binding.editPregnancies.text.toString().toInt() else 0
                }
                viewModel.save(model)

                if (this.isFirstAccess) {
                    SecurityPreferences(this).storeBoolean(Constants.KEY.FIRST_ACCESS, false)
                    startActivity(Intent(this, MainActivity::class.java))
                }
                finish()
            }
            R.id.button_later -> {
                SecurityPreferences(this).storeBoolean(Constants.KEY.FIRST_ACCESS, false)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.radio_female -> {
                binding.textPregnancies.isVisible = isChecked
                binding.editPregnancies.isVisible = isChecked
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

    private fun observe() {
        viewModel.person.observe(this, Observer {
            binding.editName.setText(it.name)
            binding.editAge.setText(it.age.toString())
            binding.radioMale.isChecked = it.gender == 0
            binding.radioFemale.isChecked = it.gender == 1
            binding.editWeight.setText(it.weight.toString())
            binding.editHeight.setText(it.height.toString())
            binding.editBmi.setText(it.bmi.toString())
            binding.editGlucose.setText(it.glucose.toString())
            binding.editInsulin.setText(it.insulin.toString())
            binding.editBloodPressure.setText(it.bloodPressure.toString())
            binding.editSkinThickness.setText(it.skinThickness.toString())
            binding.spinnerFamilyHistory.setSelection(it.familyHistory)
            binding.editPregnancies.setText(it.pregnancies.toString())
        })
    }

    private fun checkFirstAccess() {
        isFirstAccess = SecurityPreferences(this).getBoolean(Constants.KEY.FIRST_ACCESS)
        binding.buttonLater.isVisible = isFirstAccess
    }

    private fun loadData() {
        viewModel.loadData()

        //Layout e valores do spinner de Histórico Familiar
        val spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.values_family_history, R.layout.spinner_list)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.spinnerFamilyHistory.adapter = spinnerAdapter
    }

    private fun EditText.inputFilterDecimal(maxDigitsIncludingPoint: Int, maxDecimalPlaces: Int) {
        try {
            filters = arrayOf<InputFilter>(
                DecimalDigitsInputFilter(
                    maxDigitsIncludingPoint,
                    maxDecimalPlaces
                )
            )
        } catch (e: PatternSyntaxException) {
            isEnabled = false
            hint = e.message
        }
    }

    private fun setBMICalculatorListener() {
        binding.editWeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) = try {
                var height = binding.editHeight.text.toString().toDouble()
                var weight = s.toString().toDouble()

                if (height > 0.0 && weight > 0.0) {
                    binding.editBmi.setText(viewModel.calculateBMI(weight, height))
                } else {
                    binding.editBmi.setText("0.0")
                }
            } catch (ex: NumberFormatException) {
                binding.editBmi.setText("0.0")
            }
        })
        binding.editHeight.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) = try {
                var height = s.toString().toDouble()
                var weight = binding.editWeight.text.toString().toDouble()

                if (height > 0.0 && weight > 0.0) {
                    binding.editBmi.setText(viewModel.calculateBMI(weight, height))
                } else {
                    binding.editBmi.setText("0.0")
                }
            } catch (ex: NumberFormatException) {
                binding.editBmi.setText("0.0")
            }
        })
    }
}