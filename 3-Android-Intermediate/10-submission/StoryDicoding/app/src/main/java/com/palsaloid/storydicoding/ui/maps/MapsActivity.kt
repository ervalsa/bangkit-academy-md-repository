package com.palsaloid.storydicoding.ui.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.palsaloid.storydicoding.HomeViewModelFactory
import com.palsaloid.storydicoding.R
import com.palsaloid.storydicoding.data.local.datastore.UserPreference
import com.palsaloid.storydicoding.data.remote.response.story.StoryResponse
import com.palsaloid.storydicoding.databinding.ActivityMapsBinding
import com.palsaloid.storydicoding.utils.UserViewModel

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapsBinding
    private lateinit var map: GoogleMap

    private lateinit var mapsViewModel: MapsViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var listStory: ArrayList<StoryResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Story Map"

        setupUserViewModel()
        setupMapsViewModel()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_map) as SupportMapFragment

        userViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                mapsViewModel.getAllStoryWithLocation(user.token)
            }
        }

        mapsViewModel.mapResult.observe(this) { story ->
            setupMaps(story)
            mapFragment.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isIndoorLevelPickerEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.isMapToolbarEnabled = true

        for (story in listStory) {
            val location = LatLng(story.lat.toDouble(), story.lon.toDouble())
            map.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(story.name)
                    .snippet(story.description)
            )
        }

        val indonesia = LatLng(-6.200000, 106.816666)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(indonesia, 5f))

        getLocation()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getLocation()
        }
    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setupMaps(stories: List<StoryResponse>) {
        listStory = ArrayList()
        for (story in stories) {
            val list = StoryResponse(
                story.id,
                story.name,
                story.description,
                story.photoUrl,
                story.createdAt,
                story.lat,
                story.lon
            )
            listStory.add(list)
        }
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(
                UserPreference.getInstance(datastore))
        )[UserViewModel::class.java]
    }

    private fun setupMapsViewModel() {
        mapsViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MapsViewModel::class.java]
    }
}