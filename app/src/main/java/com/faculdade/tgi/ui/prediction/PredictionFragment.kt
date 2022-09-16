package com.faculdade.tgi.ui.prediction

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.faculdade.tgi.R
import com.faculdade.tgi.databinding.FragmentPredictionBinding
import com.faculdade.tgi.ui.contactsform.ContactFormActivity
import com.faculdade.tgi.ui.personaldataform.PersonalDataFormActivity

class PredictionFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPredictionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PredictionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this)[PredictionViewModel::class.java]
        _binding = FragmentPredictionBinding.inflate(inflater, container, false)

        binding.buttonEdit.setOnClickListener(this)
        binding.buttonTest.setOnClickListener(this)

        viewModel.loadData()
        observe()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_edit -> handleEditData()
            R.id.button_test -> handleTest()
        }
    }

    private fun handleEditData() {
        startActivity(Intent(context, PersonalDataFormActivity::class.java))
    }

    private fun handleTest() {

    }

    private fun observe() {
        viewModel.loadData.observe(viewLifecycleOwner) {
            binding.textAge.text = it.age.toString()
            binding.textBmi.text = it.bmi.toString()
            binding.textGlucose.text = it.glucose.toString()
            binding.textInsulin.text = it.insulin.toString()
            binding.textBloodPressure.text = it.bloodPressure.toString()
            binding.textSkinThickness.text = it.skinThickness.toString()
            binding.textDiabetesPedigree.text = it.diabetesPedigree.toString()
            binding.textPregnancies.text = it.pregnancies.toString()

            if (it.gender.toString().toInt() == 0) {
                binding.viewPregnancies.isVisible = false
                binding.linearPregnancies.isVisible = false
            } else {
                binding.viewPregnancies.isVisible = true
                binding.linearPregnancies.isVisible = true
            }
        }
    }
}