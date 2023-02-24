package com.palsaloid.bluelock.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.palsaloid.bluelock.MainActivity
import com.palsaloid.bluelock.R
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val dataCharacter = intent.getParcelableExtra<CharacterModel>("key_character")

        when (item.itemId) {
            R.id.action_share -> {
                val body = "Blue Lock Player Profile\n\n" +
                        "Name\t: ${dataCharacter?.name}\n" +
                        "Gender\t: ${dataCharacter?.gender}\n" +
                        "Age\t: ${dataCharacter?.age}\n" +
                        "Blood Type\t: ${dataCharacter?.bloodType}\n" +
                        "Hair Color\t: ${dataCharacter?.hairColor}\n" +
                        "Eye Color\t: ${dataCharacter?.eyeColor}"
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, body)
                startActivity(Intent.createChooser(shareIntent, "Share ${dataCharacter?.name} profile" ))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}