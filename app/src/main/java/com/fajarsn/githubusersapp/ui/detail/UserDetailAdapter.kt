package com.fajarsn.githubusersapp.ui.detail

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fajarsn.githubusersapp.data.entity.RepositoryResponse
import com.fajarsn.githubusersapp.databinding.RepositoryItemBinding
import java.text.SimpleDateFormat

class UserDetailAdapter(private val repoList: List<RepositoryResponse>) :
    RecyclerView.Adapter<UserDetailAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val repo = repoList[position]

        holder.binding.apply {
            repositoryNameTextView.text = repo.name
            repositoryDescriptionTextView.text = repo.description
            starCountTextView.text = "${repo.stargazersCount}"
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val time = sdf.parse(repo.updatedAt).time
            val now = System.currentTimeMillis()
            val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
            lastUpdateTextView.text = "Updated ${ago ?: repo.updatedAt}"
        }
    }

    override fun getItemCount() = repoList.size

    inner class ListViewHolder(var binding: RepositoryItemBinding) : RecyclerView.ViewHolder(binding.root)
}