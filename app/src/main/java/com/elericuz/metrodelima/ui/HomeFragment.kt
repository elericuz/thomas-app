package com.elericuz.metrodelima.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.elericuz.metrodelima.R
import com.elericuz.metrodelima.ui.adapters.TransactionAdapter
import com.elericuz.metrodelima.ui.viewmodels.TransactionViewModel
import com.elericuz.metrodelima.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TransactionAdapter
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var externalNumber: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = requireActivity()
        binding.home = this

        Timber.plant(Timber.DebugTree())

        externalNumber = HomeFragmentArgs.fromBundle(requireArguments()).externalNumber
        binding.externalNumber.text = externalNumber

        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        transactionViewModel.updateExternalNumber(externalNumber)

        adapter = TransactionAdapter()

        lifecycleScope.launch {
            transactionViewModel.getTransactions()
            transactionViewModel.transactions.collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.addLoadStateListener {
            if (it.append.endOfPaginationReached) {
                if (adapter.itemCount <= 0) {
                    binding.noRecordsFoundContainer.visibility = View.VISIBLE
                    binding.transactionsRecyclerView.visibility = View.GONE
                }
            }
        }

        binding.transactionsRecyclerView.adapter = adapter
        binding.transactionsRecyclerView.setHasFixedSize(true)
        binding.transactionsRecyclerView.setItemViewCacheSize(16)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}