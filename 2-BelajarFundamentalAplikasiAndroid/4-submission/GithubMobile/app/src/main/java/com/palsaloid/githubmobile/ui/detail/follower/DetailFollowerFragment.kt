package com.palsaloid.githubmobile.ui.detail.follower

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
import com.palsaloid.githubmobile.databinding.FragmentFollowerBinding
import com.palsaloid.githubmobile.ui.detail.DetailFragmentArgs
import com.palsaloid.githubmobile.ui.detail.DetailViewModel
import com.palsaloid.githubmobile.utils.FollowAdapter

class DetailFollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.listFollowers.observe(viewLifecycleOwner) { listFollower ->
            setListUserFollowerData(listFollower)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUserFollower.layoutManager = layoutManager
        binding.rvUserFollower.setHasFixedSize(true)

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListUserFollowerData(listFollower: List<UserResponse>) {
        val adapter = FollowAdapter(listFollower)
        binding.rvUserFollower.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}