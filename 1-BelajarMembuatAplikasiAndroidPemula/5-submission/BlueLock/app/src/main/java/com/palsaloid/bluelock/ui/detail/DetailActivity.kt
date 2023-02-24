package com.palsaloid.bluelock.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.palsaloid.bluelock.MainActivity
import com.palsaloid.bluelock.databinding.ActivityDetailBinding
import com.palsaloid.bluelock.model.CharacterModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Character Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val dataCharacter = intent.getParcelableExtra<CharacterModel>("key_character")
        binding.tvName.text = dataCharacter!!.name
        Glide.with(this)
            .load(dataCharacter.image)
            .into(binding.imgPhoto)

        binding.tvDescription.text = dataCharacter.description
        binding.includeDataCharacter.tvGender.text = dataCharacter.gender
        binding.includeDataCharacter.tvAge.text = dataCharacter.age.toString()
        binding.includeDataCharacter.tvBirthday.text = dataCharacter.birthday
        binding.includeDataCharacter.tvBloodType.text = dataCharacter.bloodType
        binding.includeDataCharacter.tvHairColor.text = dataCharacter.hairColor
        binding.includeDataCharacter.tvEyeColor.text = dataCharacter.eyeColor

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}