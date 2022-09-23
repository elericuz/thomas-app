package com.elericuz.metrodelima.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elericuz.metrodelima.api.ApiService
import com.elericuz.metrodelima.models.Transaction
import timber.log.Timber

class PagingSource(
    val externalNumber: String
): PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val currentPage = params.key ?: 1
            val response = ApiService.retrofitService.getTransactions(externalNumber, currentPage).await()
            val responseData = mutableListOf<Transaction>()
            responseData.addAll(response.results.data)

            val endOfPaginationReached = response.results.data.isEmpty()

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = if (endOfPaginationReached) null else currentPage.plus(1)
            )
        } catch (e: Exception) {
            Timber.e(e.localizedMessage)
            LoadResult.Error(e)
        }
    }
}