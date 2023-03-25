package com.palsaloid.githubmobile.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.palsaloid.githubmobile.databinding.FragmentFavoriteBinding
import com.palsaloid.githubmobile.utils.FavoriteViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding?.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: FavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(requireActivity())
        val viewModel: FavoriteViewModel by viewModels { factory }

        val usersAdapter = FavoriteAdapter { users ->
            if (users.isFavorite) {
                viewModel.deleteUsers(users)
            } else {
                viewModel.saveUsers(users)
            }
        }

        viewModel.getFavoritedUsers().observe(viewLifecycleOwner) { favoritedUsers ->
            usersAdapter.submitList(favoritedUsers)
        }

        binding?.rvFavorite?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = usersAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}