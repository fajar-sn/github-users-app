package com.fajarsn.githubusersapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fajarsn.githubusersapp.data.entity.UserResponse
import com.fajarsn.githubusersapp.databinding.UserItemBinding

class UserListAdapter(private val userList: List<UserResponse>) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {
    inner class ListViewHolder(var binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = userList[position]

        holder.binding.apply {
            usernameItemTextView.text = user.username

            Glide.with(holder.itemView.context).load(user.avatarUrl).circleCrop()
                .into(imageItemImageView)
        }
    }

    override fun getItemCount() = userList.size
}