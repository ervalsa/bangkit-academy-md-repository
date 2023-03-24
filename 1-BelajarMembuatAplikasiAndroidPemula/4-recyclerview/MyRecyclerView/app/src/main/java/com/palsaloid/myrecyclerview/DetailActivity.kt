package com.palsaloid.myrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.palsaloid.myrecyclerview.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<Hero>(EXTRA_DATA)

        supportActionBar?.title = "Detail Hero"

        binding.tvItemName.text = data?.name
        binding.tvItemDescription.text = data?.description

        Glide.with(binding.root)
            .load(data?.photo)
            .into(binding.ivHero)
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}