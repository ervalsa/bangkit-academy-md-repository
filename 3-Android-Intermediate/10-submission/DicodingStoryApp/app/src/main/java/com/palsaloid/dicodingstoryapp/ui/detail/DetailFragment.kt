package com.palsaloid.dicodingstoryapp.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.palsaloid.dicodingstoryapp.R
import com.palsaloid.dicodingstoryapp.data.Result
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.databinding.FragmentDetailBinding
import com.palsaloid.dicodingstoryapp.utils.UserViewModel
import com.palsaloid.dicodingstoryapp.utils.UserViewModelFactory
import com.palsaloid.dicodingstoryapp.utils.withDateFormat

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var userViewModel: UserViewModel
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUserViewModel()

        val detailStory = DetailFragmentArgs.fromBundle(arguments as Bundle).id

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        userViewModel.getUser().observe(requireActivity()) { user ->
            detailViewModel.detailStory(user.token, detailStory)
        }

        detailViewModel.storyResult.observe(viewLifecycleOwner) { result ->
            val tvName = binding.tvNameStory
            val tvDate = binding.tvDate
            val tvDescription = binding.tvDescriptionStory
            val image = binding.imgStory

            when (result) {
                is Result.Loading -> showLoading(true)

                is Result.Success -> {
                    tvName.text = result.data.story.name
                    tvDate.withDateFormat(result.data.story.createdAt)
                    tvDescription.text = result.data.story.description

                    Glide.with(binding.root)
                        .load(result.data.story.photoUrl)
                        .placeholder(R.drawable.ic_replay)
                        .into(image)
                }

                is Result.Error -> {
                    Toast.makeText(
                        requireActivity(),
                        resources.getString(R.string.detail_status),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            requireActivity(),
            UserViewModelFactory(
                UserPreference.getInstance(requireActivity().dataStore))
        )[UserViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}