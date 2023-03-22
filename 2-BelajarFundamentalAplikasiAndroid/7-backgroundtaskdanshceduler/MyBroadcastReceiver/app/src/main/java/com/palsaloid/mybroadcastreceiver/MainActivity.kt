package com.palsaloid.mybroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.palsaloid.mybroadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var downloadReceiver: BroadcastReceiver
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnPermission?.setOnClickListener(this)
        binding?.btnDownload?.setOnClickListener(this)

        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.d(DownloadService.TAG, "Download Selesai")
                Toast.makeText(
                    context,
                    "Download Selesai",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        registerReceiver(downloadReceiver, downloadIntentFilter)
    }

    var requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(
                this,
                "Sms receiver permission diterima",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                this,
                "Sms receiver permission ditolak",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_permission -> requestPermissionLauncher.launch(android.Manifest.permission.RECEIVE_SMS)
            R.id.btn_download -> {
                val downloadServiceIntent = Intent(this, DownloadService::class.java)
                startService(downloadServiceIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
        binding = null
    }

    companion object {
        const val ACTION_DOWNLOAD_STATUS = "download_status"
        private const val SMS_REQUEST_CODE = 101
    }
}