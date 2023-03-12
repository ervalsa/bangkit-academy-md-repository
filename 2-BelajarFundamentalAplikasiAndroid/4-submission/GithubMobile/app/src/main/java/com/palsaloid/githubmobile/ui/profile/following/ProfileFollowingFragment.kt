package com.palsaloid.githubmobile.ui.profile.following

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import com.palsaloid.githubmobile.databinding.FragmentFollowingBinding
import com.palsaloid.githubmobile.utils.FollowAdapter
import com.palsaloid.githubmobile.ui.profile.ProfileViewModel

class ProfileFollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.listFollowing.observe(viewLifecycleOwner) { listFollowing ->
            setFollowingData(listFollowing)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUserFollowing.layoutManager = layoutManager
        binding.rvUserFollowing.setHasFixedSize(true)

        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setFollowingData(listFollowing: List<UserResponse>) {
        val adapter = FollowAdapter(listFollowing)
        binding.rvUserFollowing.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}