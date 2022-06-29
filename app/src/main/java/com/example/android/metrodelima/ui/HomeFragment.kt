package com.example.android.metrodelima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.android.metrodelima.R
import com.example.android.metrodelima.databinding.FragmentHomeBinding
import com.example.android.metrodelima.ui.adapters.TransactionAdapter
import com.example.android.metrodelima.ui.viewmodels.TransactionViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
        binding.setLifecycleOwner(requireActivity())

        Timber.plant(Timber.DebugTree())

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        adapter = TransactionAdapter()

        Timber.d("aca estamos")

        lifecycleScope.launch {
            transactionViewModel.transactions.collectLatest {
                adapter.submitData(it)
            }
        }

        binding.transactionsRecyclerView.adapter = adapter
        binding.transactionsRecyclerView.setHasFixedSize(true)
        binding.transactionsRecyclerView.setItemViewCacheSize(16)

        return binding.root
    }
}