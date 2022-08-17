package com.fajarsn.githubusersapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fajarsn.githubusersapp.data.repository.Result
import com.fajarsn.githubusersapp.databinding.FragmentUserListBinding
import com.fajarsn.githubusersapp.ui.helper.ViewModelFactory
import com.fajarsn.githubusersapp.data.entity.UserResponse
import com.fajarsn.githubusersapp.data.entity.UserSearchResponse
import com.fajarsn.githubusersapp.ui.helper.BaseFragment
import com.fajarsn.githubusersapp.ui.helper.ViewHelper

@Suppress("UNCHECKED_CAST")
class UserListFragment : BaseFragment() {
    private lateinit var usernameSearchEditText: EditText
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
        viewBinding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupView() {
        val binding = binding as FragmentUserListBinding
        usernameSearchEditText = binding.usernameSearchEditText
        progressBar = binding.userListProgressBar
        recyclerView = binding.userListRecyclerView
        errorLayout = binding.userListErrorLayout
        errorTextView = binding.userListErrorTextView
        retryButton = binding.userListRetryButton
    }

    override fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext())
        val viewModel: UserListViewModel by viewModels { factory }
        this.viewModel = viewModel

        viewModel.result.observe(requireActivity()) { result ->
            if (result == null) return@observe

            when (result) {
                Result.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    setErrorViewVisibility(result is Result.Loading)
                    recyclerView.visibility = View.GONE
                }
                is Result.Error -> {
                    progressBar.visibility = View.GONE
                    setErrorViewVisibility(result is Result.Loading)
                    errorTextView.text = result.error
                }
                is Result.Success<*> -> {
                    progressBar.visibility = View.GONE

                    val data =
                        if (result.data is List<*>) result.data as List<UserResponse>
                        else (result.data as UserSearchResponse).items

                    recyclerView.visibility = View.VISIBLE

                    recyclerView.apply {
                        visibility = View.VISIBLE
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = UserListAdapter(data).apply {
                            this.setOnItemClickCallback(object :
                                UserListAdapter.OnItemClickCallBack {
                                override fun onItemClicked(data: UserResponse) {
                                    val toUserDetailFragment =
                                        UserListFragmentDirections.actionUserListFragmentToUserDetailFragment(
                                            data)
                                    view?.findNavController()?.navigate(toUserDetailFragment)
                                }
                            })
                        }
                    }
                }
            }
        }
    }

    override fun setupAction() {
        val viewModel = viewModel as UserListViewModel

        val textWatcher =
            ViewHelper.addTextChangeListener {
                val text = "${usernameSearchEditText.text.trim()}"
                if (text.isNotBlank()) viewModel.searchUser(text)
                else viewModel.getUserList()
            }

        usernameSearchEditText.addTextChangedListener(textWatcher)
        retryButton.setOnClickListener { viewModel.getUserList() }
    }

    private fun setErrorViewVisibility(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        errorLayout.visibility = if (isLoading) View.GONE else View.VISIBLE
        errorTextView.visibility = if (isLoading) View.GONE else View.VISIBLE
        retryButton.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}