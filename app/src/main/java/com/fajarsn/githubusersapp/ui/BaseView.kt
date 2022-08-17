package com.fajarsn.githubusersapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.fajarsn.githubusersapp.R
import com.fajarsn.githubusersapp.data.repository.Result
import com.fajarsn.githubusersapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

interface BaseView {
    fun setupView()
    fun setupViewModel()
    fun setupAction()
}

class MainActivity : AppCompatActivity() {
    private var viewBinding: ViewBinding? = null
    private val binding get() = viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState != null) return
        setupView()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    private fun setupView() {
        supportActionBar?.hide()
    }
}

abstract class BaseFragment : Fragment(), BaseView {
    protected lateinit var viewModel: ViewModel
    protected var viewBinding: ViewBinding? = null
    protected val binding get() = viewBinding!!

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupViewModel()
        setupAction()
    }

    protected fun showError(progressBar: ProgressBar, message: String) {
        progressBar.visibility = View.GONE
        val errorPrefix = resources.getString(R.string.something_went_wrong)
        Snackbar.make(binding.root, "$errorPrefix. $message", Snackbar.LENGTH_SHORT).show()
    }

    protected fun observeResultLiveData(
        result: Result?,
        progressBar: ProgressBar,
        callback: () -> Unit,
    ) {
        if (result == null) return

        when (result) {
            is Result.Loading -> progressBar.visibility = View.VISIBLE
            is Result.Error -> showError(progressBar, result.error)
            is Result.Success<*> -> {
                progressBar.visibility = View.GONE
                callback()
            }
        }
    }
}

object ViewHelper {
    fun addTextChangeListener(callback: () -> Unit) = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable?) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
            callback()
    }
}