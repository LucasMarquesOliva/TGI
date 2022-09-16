package com.faculdade.tgi.ui.personaldata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.faculdade.tgi.databinding.FragmentPersonalDataBinding

class PersonalDataFragment : Fragment() {

    private var _binding: FragmentPersonalDataBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PersonalDataViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, b: Bundle?): View {
        viewModel = ViewModelProvider(this)[PersonalDataViewModel::class.java]
        _binding = FragmentPersonalDataBinding.inflate(inflater, container, false)

        observe()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun observe() {
        viewModel.text.observe(viewLifecycleOwner) {
            binding.textPersonalData.text = it
        }
    }
}