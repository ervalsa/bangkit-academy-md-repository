package com.palsaloid.storydicoding.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.palsaloid.storydicoding.StoryViewModelFactory
import com.palsaloid.storydicoding.data.Result
import com.palsaloid.storydicoding.databinding.FragmentHomeBinding
import com.palsaloid.storydicoding.views.CustomRecyclerLayout

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory: StoryViewModelFactory = StoryViewModelFactory.getInstance(requireActivity())
            val storyViewModel: StoryViewModel by viewModels { factory }

            val storyAdapter = ListStoryAdapter()

            storyViewModel.getAllStories("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXpwcW5yLU85dlNTT0ZTR2oiLCJpYXQiOjE2ODE5MTc2MjZ9.-MS3nNsh9d4umhbutViAfrnW9YoC2tHJzEyh5fTDHrg").observe(viewLifecycleOwner) { story ->
                if (story != null) {
                    when(story) {
                        is Result.Loading -> binding.progressBar.visibility = View.VISIBLE

                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            storyAdapter.submitList(story.data)
                        }

                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }

            binding.rvListStory.apply {
                val linearLayoutManager = CustomRecyclerLayout(context)
                linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
                layoutManager = linearLayoutManager
                setHasFixedSize(true)
                adapter = storyAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}