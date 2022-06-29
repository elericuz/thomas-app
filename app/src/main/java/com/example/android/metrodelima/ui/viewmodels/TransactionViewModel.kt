package com.example.android.metrodelima.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.android.metrodelima.Constants.PAGE_SIZE
import com.example.android.metrodelima.models.Transaction
import com.example.android.metrodelima.pagination.PagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    var transactions: Flow<PagingData<Transaction>> = emptyFlow()

    init {
        getTransactions()
    }

    private fun getTransactions() {
        transactions = Pager(
            PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { PagingSource() }
        ).flow.cachedIn(viewModelScope)
    }
}