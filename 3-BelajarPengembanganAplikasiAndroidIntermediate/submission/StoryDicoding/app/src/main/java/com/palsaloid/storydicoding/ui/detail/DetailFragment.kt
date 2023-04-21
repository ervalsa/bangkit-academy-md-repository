package com.palsaloid.storydicoding.ui.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.palsaloid.storydicoding.HomeViewModelFactory
import com.palsaloid.storydicoding.R
import com.palsaloid.storydicoding.data.Result
import com.palsaloid.storydicoding.data.local.datastore.UserPreference
import com.palsaloid.storydicoding.data.remote.response.story.StoryResponse
import com.palsaloid.storydicoding.databinding.FragmentDetailBinding
import com.palsaloid.storydicoding.domain.model.Story
import com.palsaloid.storydicoding.utils.UserViewModel

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUserViewModel()
        setupDetailViewModel()

        val detailStory = DetailFragmentArgs.fromBundle(arguments as Bundle).id
        userViewModel.getUser().observe(requireActivity()) { user ->
            detailViewModel.loadStoryData(user.token, detailStory)
            Log.e("Fragment Detail", "Token: ${user.token}\nId: $detailStory")
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        detailViewModel.storyData.observe(viewLifecycleOwner) { storyDetail ->
            binding.tvNameStory.text = storyDetail.story.name
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            requireActivity(),
            HomeViewModelFactory(
                UserPreference.getInstance(requireActivity().datastore))
        )[UserViewModel::class.java]
    }

    private fun setupDetailViewModel() {
        detailViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}