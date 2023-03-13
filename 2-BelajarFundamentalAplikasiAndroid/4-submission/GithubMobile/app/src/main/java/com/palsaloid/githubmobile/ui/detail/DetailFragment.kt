package com.palsaloid.githubmobile.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.palsaloid.githubmobile.MainActivity
import com.palsaloid.githubmobile.R
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import com.palsaloid.githubmobile.databinding.FragmentDetailBinding
import com.palsaloid.githubmobile.ui.profile.ProfileSectionsPagerAdapter

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val bottomNav: BottomNavigationView = MainActivity.binding.bottomNav
    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataLogin = DetailFragmentArgs.fromBundle(arguments as Bundle).username
        detailViewModel.loadUserData(dataLogin)

        val sectionsPagerAdapter = DetailSectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        detailViewModel.userData.observe(viewLifecycleOwner) { userData ->
            setUserData(userData)
            detailViewModel.loadListFollower(userData.login ?: "")
            detailViewModel.loadListFollowing(userData.login ?: "")
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding.btnBack.setOnClickListener {
            binding.btnBack.findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()

        bottomNav.menu.getItem(0).isChecked = true
    }

    private fun setUserData(userData: UserResponse) {
        binding.tvName.text = userData.name ?: "-"
        binding.tvUsername.text = ("@" + userData.login) ?: "-"
        binding.tvCompany.text = userData.company ?: "-"
        binding.tvLocation.text = userData.location ?: "-"
        binding.tvBio.text = userData.bio ?: "-"
        binding.tvFollowers.text = userData.followers.toString() ?: "-"
        binding.tvFollowing.text = userData.following.toString() ?: "-"
        binding.tvPublicRepo.text = userData.public_repos.toString() ?: "-"

        Glide.with(binding.root)
            .load(userData.avatarUrl)
            .placeholder(R.drawable.ic_loading)
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