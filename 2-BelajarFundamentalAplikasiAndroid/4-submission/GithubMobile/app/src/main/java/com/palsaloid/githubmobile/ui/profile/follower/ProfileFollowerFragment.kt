package com.palsaloid.githubmobile.ui.profile.follower

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.palsaloid.githubmobile.data.remote.response.UserResponse
import com.palsaloid.githubmobile.databinding.FragmentFollowerBinding
import com.palsaloid.githubmobile.ui.profile.ProfileViewModel

class ProfileFollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowerBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.listFollowers.observe(viewLifecycleOwner) { listUser ->
            setListUserData(listUser)
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.rvUserFollower.layoutManager = layoutManager
        binding.rvUserFollower.setHasFixedSize(true)

        profileViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setListUserData(listUser: ArrayList<UserResponse>) {
        val adapter = ProfileFollowerAdapter(listUser)
        binding.rvUserFollower.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}