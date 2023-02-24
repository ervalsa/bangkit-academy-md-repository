package com.palsaloid.bluelock.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palsaloid.bluelock.databinding.ItemListBinding
import com.palsaloid.bluelock.model.CharacterModel
import com.palsaloid.bluelock.ui.detail.DetailActivity


class ListCharacterAdapter(val itemClick: (position: Int, item: CharacterModel) -> Unit) : RecyclerView.Adapter<ListCharacterAdapter.ListViewHolder>() {

    private var list: ArrayList<CharacterModel> = arrayListOf()

    fun setItem(newItem: ArrayList<CharacterModel>) {
        list = newItem
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, gender, age, birthday, bloodType, hairColor, eyeColor, image) = list[position]
        holder.binding.tvName.text = name

        Glide.with(holder.binding.root)
            .load(image)
            .into(holder.binding.imgPhoto)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra("key_character", list[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int  = list.size

    class ListViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}