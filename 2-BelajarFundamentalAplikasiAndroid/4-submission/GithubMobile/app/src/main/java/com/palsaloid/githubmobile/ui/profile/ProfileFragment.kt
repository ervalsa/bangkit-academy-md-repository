package com.palsaloid.githubmobile.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.palsaloid.githubmobile.R
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import com.palsaloid.githubmobile.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = ProfileSectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        profileViewModel.user.observe(viewLifecycleOwner) { user ->
            setUserData(user)
        }

        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setUserData(userItem: UserResponse) {
        binding.tvName.text = userItem.name ?: "-"
        binding.tvBio.text = userItem.bio ?: "-"
        binding.tvCompany.text = userItem.company ?: "-"
        binding.tvFollowers.text = userItem.followers.toString() ?: "-"
        binding.tvFollowing.text = userItem.following.toString() ?: "-"
        binding.tvPublicRepo.text = userItem.public_repos.toString() ?: "-"
        binding.tvLocation.text = userItem.location ?: "-"
        binding.tvUsername.text = ("@" + userItem.login) ?: "-"

        Glide.with(binding.root)
            .load(userItem.avatarUrl)
            .into(binding.imgProfile)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.title_followers,
            R.string.title_following
        )
    }
}