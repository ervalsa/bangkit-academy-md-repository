package com.palsaloid.githubmobile.ui.detail.following

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
import com.palsaloid.githubmobile.databinding.FragmentDetailFollowerBinding
import com.palsaloid.githubmobile.databinding.FragmentDetailFollowingBinding
import com.palsaloid.githubmobile.ui.detail.DetailViewModel
import com.palsaloid.githubmobile.utils.FollowAdapter

class DetailFollowingFragment : Fragment() {

    private var _binding: FragmentDetailFollowingBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailFollowingBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
            setListFollowingData(listFollowing)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUserFollower.layoutManager = layoutManager
        binding.rvUserFollower.setHasFixedSize(true)

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListFollowingData(listFollowing: List<UserResponse>) {
        val adapter = FollowAdapter(listFollowing)
        binding.rvUserFollower.adapter = adapter
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