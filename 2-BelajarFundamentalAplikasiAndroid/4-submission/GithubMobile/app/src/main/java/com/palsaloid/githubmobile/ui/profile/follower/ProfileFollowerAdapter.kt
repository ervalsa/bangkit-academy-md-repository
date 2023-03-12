package com.palsaloid.githubmobile.ui.profile.follower

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.palsaloid.githubmobile.R
import com.palsaloid.githubmobile.data.remote.response.UserResponse

class ProfileFollowerAdapter(private val listUser: List<UserResponse>) : RecyclerView.Adapter<ProfileFollowerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follower = listUser[position]

        with(holder) {
            tvName.text = follower.name
            tvUsername.text = follower.login

            Glide.with(itemView)
                .load(follower.avatarUrl)
                .circleCrop()
                .into(imgProfile)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvUsername: TextView = view.findViewById(R.id.tv_username)
        val imgProfile: ImageView = view.findViewById(R.id.img_profile)
    }
}