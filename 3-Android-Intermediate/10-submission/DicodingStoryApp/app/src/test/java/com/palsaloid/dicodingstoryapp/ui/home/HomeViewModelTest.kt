package com.palsaloid.dicodingstoryapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.palsaloid.dicodingstoryapp.MainDispatcherRule
import com.palsaloid.dicodingstoryapp.adapter.StoryAdapter
import com.palsaloid.dicodingstoryapp.data.StoryRepository
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryItem
import com.palsaloid.dicodingstoryapp.getOrAwaitValue
import com.palsaloid.dicodingstoryapp.utils.DataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepository

    @Mock
    private lateinit var userPreference: UserPreference

    @Test
    fun `when Get Story Should Not Null and Return Data`() = runTest {
        val dummyStory = DataDummy.generateDummyStoryResponse()
        val data: PagingData<StoryItem> = StoryPagingSource.snapshot(dummyStory)
        val expectedStory = MutableLiveData<PagingData<StoryItem>>()
        expectedStory.value = data
        Mockito.`when`(storyRepository.getStories("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTNoSkY1TzFYdXJiX3JwMmwiLCJpYXQiOjE2ODI2ODYzMjN9.KyDTuzA2xWshc1sRVrk6PmpCylnkXcbKAzNi_CTIIS8")).thenReturn(expectedStory)

        val homeViewModel = HomeViewModel(storyRepository, userPreference)
        val actualStory: PagingData<StoryItem> = homeViewModel.getStories.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(dummyStory.size, differ.snapshot().size)
        assertEquals(dummyStory[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Story Empty Should Return No Data`() = runTest {
        val data: PagingData<StoryItem> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<StoryItem>>()
        expectedStory.value = data
        Mockito.`when`(storyRepository.getStories("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTNoSkY1TzFYdXJiX3JwMmwiLCJpYXQiOjE2ODI2ODYzMjN9.KyDTuzA2xWshc1sRVrk6PmpCylnkXcbKAzNi_CTIIS8")).thenReturn(expectedStory)

        val homeViewModel = HomeViewModel(storyRepository, userPreference)
        val actualStory: PagingData<StoryItem> = homeViewModel.getStories.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        assertEquals(0, differ.snapshot().size)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}

    override fun onRemoved(position: Int, count: Int) {}

    override fun onMoved(fromPosition: Int, toPosition: Int) {}

    override fun onChanged(position: Int, count: Int, payload: Any?) {}

}

class StoryPagingSource : PagingSource<Int, LiveData<List<StoryItem>>>() {

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryItem>>>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

    companion object {
        fun snapshot(items: List<StoryItem>): PagingData<StoryItem> {
            return PagingData.from(items)
        }
    }
}