package com.example.android.metrodelima.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var _externalNumber = MutableLiveData<String>("")
    val externalNumber: LiveData<String> get() = _externalNumber

    private var _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String> get() = _currentDate

    private var _currentDateFormatted = MutableLiveData<String>()
    val currentDateFormatted: LiveData<String> get() = _currentDateFormatted

    init {
        getTransactions()
    }

    fun getTransactions() {
            transactions = Pager(
                PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = { PagingSource(externalNumber.value!!) }
            ).flow.cachedIn(viewModelScope)
    }

    fun updateExternalNumber(value: String) {
        _externalNumber.value = value
    }

    fun updateCurrentDate(value: String) {
        _currentDate.value = value
    }

    fun updateCurrentDateFormatted(value: String) {
        _currentDateFormatted.value = value
    }
}