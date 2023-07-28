package com.geekbrains.translator.ui.view.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.translator.R
import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.databinding.ActivityMainBinding
import com.geekbrains.translator.ui.common.AppState
import com.geekbrains.translator.ui.common.BaseActivity
import com.geekbrains.translator.ui.view.adapters.MainAdapter
import com.geekbrains.translator.ui.view.pages.SearchDialogFragment

class MainActivity : BaseActivity<AppState>() {

    override val viewModel: MainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    private val observer = Observer<AppState> { renderData(it) }

    private var adapter: MainAdapter? = null

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(dataItem: DataModel) {
                Toast.makeText(
                    this@MainActivity, dataItem.text, Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(
                object : SearchDialogFragment.OnSearchClickListener {
                    override fun onClick(word: String) {
                        viewModel.getData(word, true)
                            .observe(this@MainActivity, observer)
                    }
                }
            )
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun renderData(appState: AppState) {
        when (appState) {

            is AppState.Success -> {
                val dataModel = appState.data

                if (dataModel == null || dataModel.isEmpty()) {
                    showErrorScreen(
                        getString(R.string.empty_server_response_on_success)
                    )
                } else {
                    showViewSuccess()

                    if (adapter == null) {
                        binding.translations.layoutManager =
                            LinearLayoutManager(applicationContext)

                        binding.translations.adapter =
                            MainAdapter(onListItemClickListener, dataModel)
                    } else {
                        adapter!!.setData(dataModel)
                    }
                }
            }

            is AppState.Loading -> {
                showViewLoading()

                binding.progress.visibility =
                    if (appState.progress != null) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
            }

            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()

        binding.error.text = error ?: getString(R.string.undefined_error)
        binding.reload.setOnClickListener {
            viewModel.getData("hi", true).observe(this, observer)
        }
    }

    private fun showViewSuccess() {
        binding.successGroup.visibility = View.VISIBLE
        binding.loadingGroup.visibility = View.GONE
        binding.errorGroup.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.successGroup.visibility = View.GONE
        binding.loadingGroup.visibility = View.VISIBLE
        binding.errorGroup.visibility = View.GONE
    }

    private fun showViewError() {
        binding.successGroup.visibility = View.GONE
        binding.loadingGroup.visibility = View.GONE
        binding.errorGroup.visibility = View.VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "b0956816-286e-11ee-be56-0242ac120002"
    }
}