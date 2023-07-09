package com.palsaloid.storydicoding.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import com.palsaloid.storydicoding.utils.AppExecutors

abstract class NetworkBoundResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Result<ResultType>>()

    init {
        result.value = Result.Loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)

            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Result.Success(newData)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResult<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {

        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = Result.Loading(newData)
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when(response) {
                is ApiResult.Success -> {
                    mExecutors.diskIO().execute {
                        saveCallResult(response.data)
                        mExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Result.Success(newData)
                            }
                        }
                    }
                }

                is ApiResult.Empty -> mExecutors.mainThread().execute {
                    result.addSource(loadFromDB()) { newData ->
                        result.value = Result.Success(newData)
                    }
                }

                is ApiResult.Error -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        result.value = Result.Error(response.errorMessage, newData)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Result<ResultType>> = result
}