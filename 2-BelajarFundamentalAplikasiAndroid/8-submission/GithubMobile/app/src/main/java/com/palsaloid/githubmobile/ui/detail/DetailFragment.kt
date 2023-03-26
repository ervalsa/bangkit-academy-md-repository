package com.palsaloid.githubmobile.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.palsaloid.githubmobile.MainActivity
import com.palsaloid.githubmobile.R
import com.palsaloid.githubmobile.data.entity.UsersEntity
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import com.palsaloid.githubmobile.databinding.FragmentDetailBinding
import com.palsaloid.githubmobile.ui.favorite.FavoriteViewModel
import com.palsaloid.githubmobile.utils.FavoriteViewModelFactory

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding

    private val bottomNav: BottomNavigationView = MainActivity.binding.bottomNav
    private val detailViewModel by activityViewModels<DetailViewModel> {
        FavoriteViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: FavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(requireActivity())
        val viewModel: FavoriteViewModel by viewModels { factory }

        val dataLogin = DetailFragmentArgs.fromBundle(arguments as Bundle).username
        detailViewModel.loadUserData(dataLogin)

        val sectionsPagerAdapter = DetailSectionsPagerAdapter(this)
        binding?.viewPager?.adapter = sectionsPagerAdapter

        binding?.tabs?.let {
            binding?.viewPager?.let { it1 ->
                TabLayoutMediator(it, it1) { tab, position ->
                    tab.text = resources.getString(TAB_TITLES[position])
                }.attach()
            }
        }

        detailViewModel.userData.observe(viewLifecycleOwner) { detailUser ->
            setUserData(detailUser)
        }

        viewModel.isFavoriteUser(dataLogin).observe(viewLifecycleOwner) { user ->
            if (user != null) {
                setFavorite(true)
                binding?.imgFavorite?.setOnClickListener {
                    detailViewModel.deleteUser(user)
                    Toast.makeText(
                        requireContext(),
                        "Berhasil menghapus user favorite",
                        Toast.LENGTH_SHORT
                    ).show()
                    setFavorite(false)
                }
            } else {
                setFavorite(false)
            }
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        binding?.btnBack?.setOnClickListener {
            binding?.btnBack?.findNavController()?.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()

        if (bottomNav.menu.getItem(0).isChecked) {
            bottomNav.menu.getItem(0).isChecked = true
        } else if (bottomNav.menu.getItem(1).isChecked){
            bottomNav.menu.getItem(1).isChecked = true
        }
    }

    private fun setUserData(detailUser: UserResponse) {
        binding?.tvName?.text = detailUser.name ?: "-"
        binding?.tvUsername?.text = ("@" + detailUser.login) ?: "-"
        binding?.tvCompany?.text = detailUser.company ?: "-"
        binding?.tvLocation?.text = detailUser.location ?: "-"
        binding?.tvBio?.text = detailUser.bio ?: "-"
        binding?.tvFollowers?.text = detailUser.followers.toString() ?: "-"
        binding?.tvFollowing?.text = detailUser.following.toString() ?: "-"
        binding?.tvPublicRepo?.text = detailUser.public_repos.toString() ?: "-"

        binding?.root?.let {
            binding?.imgProfile?.let { it1 ->
                Glide.with(it)
                    .load(detailUser.avatarUrl)
                    .placeholder(R.drawable.ic_loading)
                    .into(it1)
            }
        }

        val data = UsersEntity(
            detailUser.name.toString(),
            detailUser.login.toString(),
            detailUser.avatarUrl.toString(),
            true
        )

        binding?.imgFavorite?.setOnClickListener {
            detailViewModel.insertUser(data)
            Toast.makeText(
                requireContext(),
                "Berhasil menambahkan user favorite",
                Toast.LENGTH_SHORT
            ).show()
            setFavorite(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setFavorite(isFavorite: Boolean) {
        val imgFavorite = binding?.imgFavorite
        if (isFavorite) {
            imgFavorite?.setImageDrawable(ContextCompat.getDrawable(imgFavorite.context, R.drawable.ic_favorite))
        } else {
            imgFavorite?.setImageDrawable(ContextCompat.getDrawable(imgFavorite.context, R.drawable.ic_favorite_border))
        }
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