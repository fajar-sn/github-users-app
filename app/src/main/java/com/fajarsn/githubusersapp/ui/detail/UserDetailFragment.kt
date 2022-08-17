package com.fajarsn.githubusersapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fajarsn.githubusersapp.R
import com.fajarsn.githubusersapp.data.entity.RepositoryResponse
import com.fajarsn.githubusersapp.data.entity.UserDetailResponse
import com.fajarsn.githubusersapp.data.entity.UserResponse
import com.fajarsn.githubusersapp.data.repository.Result
import com.fajarsn.githubusersapp.databinding.FragmentUserDetailBinding
import com.fajarsn.githubusersapp.ui.helper.BaseFragment
import com.fajarsn.githubusersapp.ui.helper.ViewModelFactory
import com.fajarsn.githubusersapp.ui.main.UserListViewModel

@Suppress("UNCHECKED_CAST")
class UserDetailFragment : BaseFragment() {
    private lateinit var response: UserResponse
    private lateinit var fullNameTextView: TextView
    private lateinit var usernameTextView: TextView
    private lateinit var profileImageView: ImageView
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
        val binding = binding as FragmentUserDetailBinding
        response = UserDetailFragmentArgs.fromBundle(arguments as Bundle).user
        fullNameTextView = binding.fullNameDetailTextView
        usernameTextView = binding.usernameDetailTextView
        profileImageView = binding.imageDetailImageView
        progressBar = binding.repoListProgressBar
        bioTextView = binding.bioDetailTextView
        recyclerView = binding.repoListRecyclerView

        usernameTextView.text = response.username
        Glide.with(requireContext()).load(response.avatarUrl).circleCrop()
            .into(profileImageView)
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel: UserDetailViewModel by viewModels { factory }
        this.viewModel = viewModel
        viewModel.getUserDetail(response.username)
        viewModel.getUserRepoList(response.username)

        viewModel.userDetailResult.observe(requireActivity()) { result ->
            if (result == null) return@observe

            when (result) {
                is Result.Error -> showError(progressBar, result.error)
                Result.Loading -> {}
                is Result.Success<*> -> {
                    val data = result.data as UserDetailResponse
                    bioTextView.text = data.bio ?: ""
                    fullNameTextView.text = data.name
                }
            }
        }

        viewModel.repoListResult.observe(requireActivity()) { result ->
            if (result == null) return@observe

            when (result) {
                is Result.Error -> showError(progressBar, result.error)
                Result.Loading -> progressBar.visibility = View.VISIBLE
                is Result.Success<*> -> {
                    progressBar.visibility = View.GONE
                    val data : List<RepositoryResponse> = result.data as List<RepositoryResponse>

                    recyclerView.apply {
                        visibility = View.VISIBLE
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = UserDetailAdapter(data)
                    }
                }
            }
        }
    }

    override fun setupAction() {}
}