package com.example.android.metrodelima.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android.metrodelima.api.ApiService
import com.example.android.metrodelima.models.Transaction

class PagingSource(): PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val currentPage = params.key ?: 1
            val response = ApiService.retrofitService.getTransactions(currentPage).await()
            val responseData = mutableListOf<Transaction>()
            responseData.addAll(response.results.data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}