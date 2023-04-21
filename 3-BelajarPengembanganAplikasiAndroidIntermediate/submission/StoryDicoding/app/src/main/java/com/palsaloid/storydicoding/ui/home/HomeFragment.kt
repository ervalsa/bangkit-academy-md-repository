package com.palsaloid.storydicoding.ui.home

import android.content.Context
import android.content.Intent
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
import com.palsaloid.storydicoding.HomeViewModelFactory
import com.palsaloid.storydicoding.StoryViewModelFactory
import com.palsaloid.storydicoding.data.Result
import com.palsaloid.storydicoding.data.local.datastore.UserPreference
import com.palsaloid.storydicoding.databinding.FragmentHomeBinding
import com.palsaloid.storydicoding.ui.auth.login.LoginActivity
import com.palsaloid.storydicoding.ui.detail.DetailFragment
import com.palsaloid.storydicoding.utils.StoryViewModel
import com.palsaloid.storydicoding.utils.UserViewModel
import com.palsaloid.storydicoding.views.CustomRecyclerLayout

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

        setupUserViewModel()

        if (activity != null) {
            val factory: StoryViewModelFactory = StoryViewModelFactory.getInstance(requireActivity())
            val storyViewModel: StoryViewModel by viewModels { factory }

            val storyAdapter = ListStoryAdapter()

            userViewModel.getUser().observe(requireActivity()) { user ->
                if (user.isLogin) {
                    storyViewModel.getAllStories(user.token).observe(viewLifecycleOwner) { story ->
                        if (story != null) {
                            when(story) {
                                is Result.Loading -> binding.progressBar.visibility = View.VISIBLE

                                is Result.Success -> {
                                    binding.progressBar.visibility = View.GONE
                                    storyAdapter.submitList(story.data)
                                }

                                is Result.Error -> {
                                    binding.progressBar.visibility = View.GONE
                                }
                            }
                        }
                    }
                } else {
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }

            binding.rvListStory.apply {
                val linearLayoutManager = CustomRecyclerLayout(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                layoutManager = linearLayoutManager
                setHasFixedSize(true)
                adapter = storyAdapter
            }
        }
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(
                UserPreference.getInstance(requireActivity().datastore))
        )[UserViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}