package com.palsaloid.githubmobile.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palsaloid.githubmobile.R
import com.palsaloid.githubmobile.data.remote.response.UserResponse

class HomeAdapter(private val listUser: List<UserResponse>) : RecyclerView.Adapter<HomeAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = listUser[position]
        holder.tvName.text = user.login
        holder.tvUsername.text = "@" + user.login

        Glide.with(holder.itemView)
            .load(user.avatarUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_loading)
            .into(holder.imgProfile)

        holder.itemView.setOnClickListener {
            val toDetailFragment = HomeFragmentDirections.actionNavigationHomeToNavigationDetail()
            toDetailFragment.username = user.login.toString()
            holder.itemView.findNavController().navigate(toDetailFragment)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
        val imgProfile: ImageView = itemView.findViewById(R.id.img_profile)
    }
}