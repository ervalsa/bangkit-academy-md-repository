package com.palsaloid.dicodingstoryapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.palsaloid.dicodingstoryapp.adapter.StoryAdapter
import com.palsaloid.dicodingstoryapp.customview.CustomRecyclerLayout
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.databinding.FragmentHomeBinding
import com.palsaloid.dicodingstoryapp.ui.StoryViewModelFactory
import com.palsaloid.dicodingstoryapp.utils.LoadingStateAdapter
import com.palsaloid.dicodingstoryapp.utils.UserViewModel
import com.palsaloid.dicodingstoryapp.utils.UserViewModelFactory

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userViewModel: UserViewModel


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
        setupUserViewModel()

        val storyAdapter = StoryAdapter()

        userViewModel.getUser().observe(viewLifecycleOwner) { result ->
            viewModel.getStories(result.token).observe(viewLifecycleOwner) { stories ->
                storyAdapter.submitData(lifecycle, stories)
            }
        }

        binding.rvListStory.apply {
            val linearlayoutManager = CustomRecyclerLayout(context)
            linearlayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearlayoutManager

            setHasFixedSize(true)
            adapter = storyAdapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter {
                    storyAdapter.retry()
                },
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
        }
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(
                UserPreference.getInstance(requireActivity().datastore))
        )[UserViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}