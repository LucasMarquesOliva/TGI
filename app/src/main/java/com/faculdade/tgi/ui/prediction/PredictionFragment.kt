package com.faculdade.tgi.ui.prediction

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.faculdade.tgi.R
import com.faculdade.tgi.databinding.FragmentPredictionBinding
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
        binding.linearResult.visibility = View.INVISIBLE
        binding.progressResult.visibility = View.GONE
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
        Thread {
            //Mostra carregamento
            activity?.runOnUiThread {
                binding.progressResult.visibility = View.VISIBLE
            }

            //Pega os valores da tela
            val age = binding.textAge.text.toString().toInt()
            val bmi = binding.textBmi.text.toString().toDouble()
            val glucose = binding.textGlucose.text.toString().toDouble()
            val insulin = binding.textInsulin.text.toString().toDouble()
            val bloodPressure = binding.textBloodPressure.text.toString().toDouble()
            val skinThickness = binding.textSkinThickness.text.toString().toDouble()
            val diabetesPedigree = binding.textDiabetesPedigree.text.toString().toDouble()
            val pregnancies = binding.textPregnancies.text.toString().toInt()

            //Instancia o python e chama o método de predição
            if (!Python.isStarted()) {
                Python.start(AndroidPlatform(context!!))
            }
            val python = Python.getInstance()
            val pythonFile = python.getModule("prediction")
            val result =
                pythonFile.callAttr(
                    "predict",
                    pregnancies,
                    glucose,
                    bloodPressure,
                    skinThickness,
                    insulin,
                    bmi,
                    diabetesPedigree,
                    age
                ).toString().toInt()

            //Atualiza a tela com o resultado
            activity?.runOnUiThread {
                if (result == 1) {
                    binding.imageResult.setImageResource(R.drawable.ic_dissatisfied)
                    binding.textResult.text = resources.getString(R.string.positive_test)
                } else {
                    binding.imageResult.setImageResource(R.drawable.ic_satisfied)
                    binding.textResult.text = resources.getString(R.string.negative_test)
                }
                binding.linearResult.visibility = View.VISIBLE
                binding.progressResult.visibility = View.GONE
            }

            //Atualiza o banco de dados com o resultado
            viewModel.updateResult(result)
        }.start()
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

            //Exibe a quantidade de vezes que engravidou apenas se for do sexo feminino
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