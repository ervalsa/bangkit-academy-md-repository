package com.palsaloid.dicodingstoryapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.palsaloid.dicodingstoryapp.R
import com.palsaloid.dicodingstoryapp.adapter.StoryAdapter
import com.palsaloid.dicodingstoryapp.customview.CustomRecyclerLayout
import com.palsaloid.dicodingstoryapp.data.Result
import com.palsaloid.dicodingstoryapp.databinding.FragmentHomeBinding
import com.palsaloid.dicodingstoryapp.ui.StoryViewModelFactory
import com.palsaloid.dicodingstoryapp.utils.LoadingStateAdapter

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: StoryViewModelFactory = StoryViewModelFactory.getInstance(requireActivity())
        val viewModel: HomeViewModel by viewModels { factory }

        val storyAdapter = StoryAdapter()

        viewModel.getStories("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTNoSkY1TzFYdXJiX3JwMmwiLCJpYXQiOjE2ODI2ODYzMjN9.KyDTuzA2xWshc1sRVrk6PmpCylnkXcbKAzNi_CTIIS8").observe(viewLifecycleOwner) { result ->
            storyAdapter.submitData(lifecycle, result)
        }

        binding.rvListStory.apply {
            val linearlayoutManager = CustomRecyclerLayout(context)
            linearlayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearlayoutManager

            setHasFixedSize(true)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}