package com.palsaloid.bluelock

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.palsaloid.bluelock.adapter.ListCharacterAdapter
import com.palsaloid.bluelock.databinding.ActivityMainBinding
import com.palsaloid.bluelock.model.CharacterModel
import com.palsaloid.bluelock.ui.profile.ProfileActivity
import com.palsaloid.bluelock.utils.CharacterData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val listCharacterAdapter by lazy {
        ListCharacterAdapter { position: Int, _: CharacterModel ->
            binding.rvCharacter.smoothScrollToPosition(position)
        }
    }

    private var listCharacter: ArrayList<CharacterModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        listCharacter.addAll(CharacterData.listData)
        showRecycler()

        binding.btnProfile.setOnClickListener {
            val profileIntent = Intent(this, ProfileActivity::class.java)
            startActivity(profileIntent)
        }

        binding.btnHamburger.setOnClickListener {
            Toast.makeText(this, "Hamburger Button", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showRecycler() {
        binding.rvCharacter.initialize(listCharacterAdapter)
        listCharacterAdapter.setItem(listCharacter)
    }
}