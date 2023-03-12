package com.palsaloid.githubmobile.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.palsaloid.githubmobile.R
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import com.palsaloid.githubmobile.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.listUser.observe(viewLifecycleOwner) { listUser ->
            setUserData(listUser)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.setHasFixedSize(true)

        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUserData(listUser: List<UserResponse>) {
        val adapter = HomeAdapter(listUser)
        binding.rvUser.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}