package com.example.android.metrodelima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.metrodelima.R
import com.example.android.metrodelima.databinding.FragmentHomeBinding
import com.example.android.metrodelima.ui.adapters.ClickListener
import com.example.android.metrodelima.ui.adapters.TransactionAdapter
import com.example.android.metrodelima.ui.viewmodels.TransactionViewModel
import com.example.android.metrodelima.utils.Utils.Companion.hideKeyboard
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TransactionAdapter
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = requireActivity()
        binding.home = this

        Timber.plant(Timber.DebugTree())

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        transactionViewModel.updateCurrentDateFormatted("06-06-2022")

        adapter = TransactionAdapter(
            ClickListener {
                transactionViewModel.updateExternalNumber(it.external_number)
                binding.externalNumber.setText(it.external_number)
            }
        )

        lifecycleScope.launch {
            transactionViewModel.transactions.collectLatest {
                adapter.submitData(it)
            }
        }

        transactionViewModel.externalNumber.observe(requireActivity(), Observer {
            transactionViewModel.getTransactions()
            adapter.refresh()
        })

        binding.transactionsRecyclerView.adapter = adapter
        binding.transactionsRecyclerView.setHasFixedSize(true)
        binding.transactionsRecyclerView.setItemViewCacheSize(16)

        return binding.root
    }

    fun filterClick() {
        findNavController().navigate(HomeFragmentDirections.actionHomeToFilter())
    }

    fun searchExternalNumber() {
        transactionViewModel.updateExternalNumber(binding.externalNumber.text.toString().trim())
        binding.externalNumber.hideKeyboard()
    }

    fun clearExternalNumber() {
        transactionViewModel.updateExternalNumber("")
        binding.externalNumber.setText("")
        binding.externalNumber.hideKeyboard()
    }
}