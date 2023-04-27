package com.palsaloid.storydicoding.ui.maps

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.palsaloid.storydicoding.HomeViewModelFactory
import com.palsaloid.storydicoding.R
import com.palsaloid.storydicoding.data.local.datastore.UserPreference
import com.palsaloid.storydicoding.databinding.FragmentMapsBinding
import com.palsaloid.storydicoding.utils.UserViewModel

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MapsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapsViewModel: MapsViewModel
    private lateinit var userViewModel: UserViewModel

    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUserViewModel()
        setupMapsViewModel()

        if (activity != null) {
            val mapFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragment_container_map) as? SupportMapFragment
            mapFragment?.getMapAsync(this)
        }
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            requireActivity(),
            HomeViewModelFactory(
                UserPreference.getInstance(requireActivity().datastore))
        )[UserViewModel::class.java]
    }

    private fun setupMapsViewModel() {
        mapsViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[MapsViewModel::class.java]
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}