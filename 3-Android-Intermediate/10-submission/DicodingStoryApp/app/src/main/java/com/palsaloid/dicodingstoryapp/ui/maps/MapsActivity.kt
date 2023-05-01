package com.palsaloid.dicodingstoryapp.ui.maps

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import com.palsaloid.dicodingstoryapp.R
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.data.remote.response.story.StoryItem
import com.palsaloid.dicodingstoryapp.databinding.ActivityMapsBinding
import com.palsaloid.dicodingstoryapp.utils.UserViewModel
import com.palsaloid.dicodingstoryapp.utils.UserViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name ="settings")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel by viewModels<MapsViewModel>()
    private lateinit var userViewModel: UserViewModel

    private lateinit var mMap: GoogleMap
    private lateinit var listStory: ArrayList<StoryItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUserViewModel()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_map) as SupportMapFragment

        userViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                mapsViewModel.getStoriesLocation(user.token)
            }
        }

        mapsViewModel.mapResult.observe(this) { stories ->
            setupMapData(stories)
            mapFragment.getMapAsync(this)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        for (story in listStory) {
            val storyLocation = LatLng(story.lat.toDouble(), story.lon.toDouble())
            mMap.addMarker(
                MarkerOptions()
                    .position(storyLocation)
                    .title(story.name)
                    .snippet(story.description)
            )
        }

        val indonesiaLocation = LatLng(-7.161367, 113.482498)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(indonesiaLocation, 3f))

        getLocation()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getLocation()
        }
    }

    private fun getLocation(){
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setupMapData(stories: List<StoryItem>) {
        listStory = ArrayList()

        for (story in stories) {
            val storyItem = StoryItem(
                story.id,
                story.name,
                story.description,
                story.photoUrl,
                story.createdAt,
                story.lat,
                story.lon
            )
            listStory.add(storyItem)
        }
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(
                UserPreference.getInstance(dataStore))
        )[UserViewModel::class.java]
    }
}