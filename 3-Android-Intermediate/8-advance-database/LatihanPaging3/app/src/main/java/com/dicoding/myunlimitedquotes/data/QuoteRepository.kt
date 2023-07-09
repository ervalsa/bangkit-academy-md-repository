package com.dicoding.myunlimitedquotes.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.myunlimitedquotes.database.QuoteDatabase
import com.dicoding.myunlimitedquotes.network.ApiService
import com.dicoding.myunlimitedquotes.network.QuoteResponseItem

class QuoteRepository(private val quoteDatabase: QuoteDatabase, private val apiService: ApiService) {

    @OptIn(ExperimentalPagingApi::class)
    fun getQuote() : LiveData<PagingData<QuoteResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = QuoteRemoteMediator(quoteDatabase, apiService),
            pagingSourceFactory = {
//                QuotePagingSource(apiService)
                quoteDatabase.quoteDao().getAllQuote()
            }
        ).liveData
    }
}