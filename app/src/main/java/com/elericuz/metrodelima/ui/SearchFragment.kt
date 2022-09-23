package com.elericuz.metrodelima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.elericuz.metrodelima.R
import com.elericuz.metrodelima.databinding.FragmentSearchBinding
import com.elericuz.metrodelima.utils.Utils.Companion.hideKeyboard

class SearchFragment : Fragment() {
    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.lifecycleOwner = requireActivity()
        binding.search = this

        return binding.root
    }

    fun searchExternalNumber() {
        if (binding.externalNumber.text.isNotEmpty()) {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchToHome(
                    binding.externalNumber.text.toString()
                )
            )
            clearExternalNumber()
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.card_number_not_entered),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun clearExternalNumber() {
        binding.externalNumber.setText("")
        binding.externalNumber.hideKeyboard()
    }
}