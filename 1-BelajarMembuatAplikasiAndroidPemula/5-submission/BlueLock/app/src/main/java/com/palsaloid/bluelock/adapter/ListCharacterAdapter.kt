package com.palsaloid.bluelock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palsaloid.bluelock.databinding.ItemListBinding
import com.palsaloid.bluelock.model.CharacterModel


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
        val (name, image) = list[position]
        holder.binding.tvName.text = name

        Glide.with(holder.binding.root)
            .load(image)
            .into(holder.binding.imgPhoto)
    }

    override fun getItemCount(): Int  = list.size

    class ListViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}