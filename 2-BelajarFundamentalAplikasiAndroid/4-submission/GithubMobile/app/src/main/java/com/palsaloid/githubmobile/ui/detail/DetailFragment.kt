package com.palsaloid.githubmobile.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.palsaloid.githubmobile.MainActivity
import com.palsaloid.githubmobile.R
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import com.palsaloid.githubmobile.databinding.FragmentDetailBinding
import com.palsaloid.githubmobile.ui.home.HomeFragment

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

        detailViewModel.userData.observe(viewLifecycleOwner) { userData ->
            setUserData(userData)
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
        binding.tvName.text = userData.name
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}