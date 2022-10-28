package com.faculdade.tgi.ui.personaldata

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.faculdade.tgi.R
import com.faculdade.tgi.databinding.FragmentPersonalDataBinding
import com.faculdade.tgi.ui.personaldataform.PersonalDataFormActivity

class PersonalDataFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPersonalDataBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PersonalDataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this)[PersonalDataViewModel::class.java]
        _binding = FragmentPersonalDataBinding.inflate(inflater, container, false)

        binding.buttonEdit.setOnClickListener(this)

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
            R.id.button_edit -> startActivity(Intent(context, PersonalDataFormActivity::class.java))
        }
    }

    private fun observe() {
        viewModel.loadData.observe(viewLifecycleOwner) {
            binding.textName.text = it.name
            binding.textAge.text = it.age.toString()
            binding.textWeight.text = it.weight.toString()
            binding.textHeight.text = it.height.toString()
            binding.textBmi.text = it.bmi.toString()
            binding.textGlucose.text = it.glucose.toString()
            binding.textInsulin.text = it.insulin.toString()
            binding.textBloodPressure.text = it.bloodPressure.toString()
            binding.textSkinThickness.text = it.skinThickness.toString()
            binding.textPregnancies.text = it.pregnancies.toString()

            //Busca o valor do Sppiner com as opções de histórico familiar pelo ID salvo
            binding.textDiabetesPedigree.text = resources.getStringArray(R.array.values_family_history)[it.familyHistory]

            //Exibe a quantidade de vezes que engravidou apenas se for do sexo feminino
            if (it.gender.toString().toInt() == 0) {
                binding.textGender.text = resources.getString(R.string.male)
                binding.viewPregnancies.isVisible = false
                binding.linearPregnancies.isVisible = false
            } else {
                binding.textGender.text = resources.getString(R.string.female)
                binding.viewPregnancies.isVisible = true
                binding.linearPregnancies.isVisible = true
            }
        }
    }
}