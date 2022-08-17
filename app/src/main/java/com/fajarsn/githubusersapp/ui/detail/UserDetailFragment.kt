package com.fajarsn.githubusersapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.fajarsn.githubusersapp.R
import com.fajarsn.githubusersapp.databinding.FragmentUserDetailBinding
import com.fajarsn.githubusersapp.ui.helper.BaseFragment

class UserDetailFragment : BaseFragment() {
    private lateinit var fullNameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var bioTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var errorLayout: LinearLayoutCompat
    private lateinit var errorTextView: TextView
    private lateinit var retryButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        TODO("Not yet implemented")
    }

    override fun setupViewModel() {
        TODO("Not yet implemented")
    }

    override fun setupAction() {
        TODO("Not yet implemented")
    }
}