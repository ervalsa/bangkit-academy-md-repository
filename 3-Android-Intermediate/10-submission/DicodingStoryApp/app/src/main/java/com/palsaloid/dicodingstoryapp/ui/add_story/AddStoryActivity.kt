package com.palsaloid.dicodingstoryapp.ui.add_story

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.palsaloid.dicodingstoryapp.MainActivity
import com.palsaloid.dicodingstoryapp.R
import com.palsaloid.dicodingstoryapp.data.Result
import com.palsaloid.dicodingstoryapp.data.local.datastore.UserPreference
import com.palsaloid.dicodingstoryapp.databinding.ActivityAddStoryBinding
import com.palsaloid.dicodingstoryapp.ui.camera.CameraActivity
import com.palsaloid.dicodingstoryapp.utils.UserViewModel
import com.palsaloid.dicodingstoryapp.utils.UserViewModelFactory
import com.palsaloid.dicodingstoryapp.utils.reduceFileImage
import com.palsaloid.dicodingstoryapp.utils.rotateBitmap
import com.palsaloid.dicodingstoryapp.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding

    private var getFile: File? = null
    private lateinit var userViewModel: UserViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var myLocation: Location? = null
    private val addStoryViewModel by viewModels<AddStoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUserViewModel()

        supportActionBar?.title = resources.getString(R.string.create_story)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val edtDescription = binding.edtDescription

        if (!allPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSION,
                REQUEST_CODE_PERMISSION
            )
        }

        addStoryViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnImageGallery.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION_GET_CONTENT
            intent.type = "image/*"

            val chooser = Intent.createChooser(intent, resources.getString(R.string.choose_image))
            launcherIntentGallery.launch(chooser)
        }

        binding.btnCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            launcherIntentCameraX.launch(intent)
        }

        binding.btnCreateStory.setOnClickListener {
            when {
                edtDescription.text.isEmpty() -> {
                    edtDescription.error = resources.getString(R.string.insert_description)
                }

                else -> {
                    if (getFile != null) {
                        val requestFile = reduceFileImage(getFile as File)
                        val requestDescription = edtDescription.text.toString().toRequestBody("text/plain".toMediaType())
                        val requestImageFile = requestFile.asRequestBody("image/jpeg".toMediaType())
                        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                            "photo",
                            requestFile.name,
                            requestImageFile
                        )

                        val lat = myLocation?.latitude?.toFloat()
                        val lon = myLocation?.longitude?.toFloat()

                        userViewModel.getUser().observe(this) { user ->
                            if (user.isLogin) {
                                addStoryViewModel.addStory(
                                    token = user.token,
                                    photo = imageMultipart,
                                    description = requestDescription,
                                    lat = lat,
                                    lon = lon
                                )
                                addStoryViewModel.addStoryResult.observe(this) { result ->
                                    when (result) {
                                        is Result.Loading -> showLoading(true)

                                        is Result.Success -> {
                                            Toast.makeText(
                                                this,
                                                resources.getString(R.string.add_story_success),
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            val intent = Intent(this, MainActivity::class.java)
                                            startActivity(intent)
                                            finishAffinity()
                                        }

                                        is Result.Error -> {
                                            Toast.makeText(
                                                this,
                                                resources.getString(R.string.add_story_failed),
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            resources.getString(R.string.picture_status),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getMyLocation()
    }

    @Suppress("DEPRECATION")
    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("picture", File::class.java)
            } else {
                result.data?.getSerializableExtra("picture")
            } as File

            val isBackCamera = result.data?.getBooleanExtra("isBackCamera", true) as Boolean
            myFile.let { file ->
                rotateBitmap(file, isBackCamera)
                getFile = file
                binding.imgPhotoStory.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK ) {
            val selectedImg: Uri = result.data?.data as Uri
            val file = uriToFile(selectedImg, this)
            getFile = file

            binding.imgPhotoStory.setImageURI(selectedImg)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (!allPermissionGranted()) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.not_getting_permission),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                getMyLocation()
            }

            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                getMyLocation()
            }

            else -> {
                binding.switchLocation.isChecked = false
                binding.switchLocation.isEnabled = false
            }
        }
    }

    private fun getMyLocation() {
        if (
            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    myLocation = it
                } else {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.enable_gps),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            this,
            UserViewModelFactory(
                UserPreference.getInstance(dataStore))
        )[UserViewModel::class.java]
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSION = 10
    }
}