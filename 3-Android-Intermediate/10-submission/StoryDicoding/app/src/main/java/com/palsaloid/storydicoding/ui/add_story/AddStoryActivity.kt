package com.palsaloid.storydicoding.ui.add_story

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.palsaloid.storydicoding.HomeViewModelFactory
import com.palsaloid.storydicoding.MainActivity
import com.palsaloid.storydicoding.R
import com.palsaloid.storydicoding.data.local.datastore.UserPreference
import com.palsaloid.storydicoding.data.remote.retrofit.ApiResult
import com.palsaloid.storydicoding.databinding.ActivityAddStoryBinding
import com.palsaloid.storydicoding.ui.add_story.camera.CameraActivity
import com.palsaloid.storydicoding.utils.UserViewModel
import com.palsaloid.storydicoding.utils.reduceFileImage
import com.palsaloid.storydicoding.utils.rotateBitmap
import com.palsaloid.storydicoding.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class AddStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStoryBinding

    private var getFile: File? = null
    private lateinit var userViewModel: UserViewModel
    private lateinit var addStoryViewModel: AddStoryViewModel
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAddStoryViewModel()
        setupUserViewModel()

        supportActionBar?.title = resources.getString(R.string.create_story)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val edt_description = binding.edtDescription

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

        binding.btnCreateStory.setOnClickListener {
            when {
                edt_description.text.isEmpty() -> {
                    edt_description.error = "Masukkan deskripsi"
                }

                else -> {
                    if (getFile != null) {
                        val requestFile = reduceFileImage(getFile as File)
                        val requestDescription = edt_description.text.toString().toRequestBody("text/plain".toMediaType())
                        val requestImageFile = requestFile.asRequestBody("image/jpeg".toMediaType())
                        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                            "photo",
                            requestFile.name,
                            requestImageFile
                        )

                        userViewModel.getUser().observe(this) { user ->
                            if (user.isLogin) {
                                addStoryViewModel.addStory(
                                    user.token,
                                    imageMultipart,
                                    requestDescription
                                )
                                Log.e("AddStoryActivity", "User Token: ${user.token}")
                                addStoryViewModel.addResult.observe(this) { result ->
                                    when(result) {
                                        is ApiResult.Empty -> {

                                        }

                                        is ApiResult.Success -> {
                                            Toast.makeText(
                                                this,
                                                "Tambah story berhasil",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            val intent = Intent(this@AddStoryActivity, MainActivity::class.java)
                                            startActivity(intent)
                                            finishAffinity()
                                        }

                                        is ApiResult.Error -> {
                                            Toast.makeText(
                                                this,
                                                "Tambah story gagal, silahkan coba lagi",
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
                            "Gambar belum dipilih",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.btnImageGallery.setOnClickListener {
            val intent = Intent()
            intent.action = ACTION_GET_CONTENT
            intent.type = "image/*"

            val chooser = Intent.createChooser(intent, "Pilih gambar")
            launcherIntentGallery.launch(chooser)
        }

        binding.btnCamera.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            launcherIntentCameraX.launch(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == CAMERA_X_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra("picture", File::class.java)
            } else {
                @Suppress("DEPRECATION")
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
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val file = uriToFile(selectedImg, this)
            getFile = file

            binding.imgPhotoStory.setImageURI(selectedImg)
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
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
                    "Tidak mendapatkan izin",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun setupAddStoryViewModel() {
        addStoryViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[AddStoryViewModel::class.java]
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(
                UserPreference.getInstance(datastore))
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