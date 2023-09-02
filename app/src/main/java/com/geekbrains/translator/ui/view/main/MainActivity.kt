package com.geekbrains.translator.ui.view.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.translator.R
import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.databinding.ActivityMainBinding
import com.geekbrains.translator.domain.inteactor.MainInteractor
import com.geekbrains.translator.ui.common.AppState
import com.geekbrains.translator.ui.common.BaseActivity
import com.geekbrains.translator.ui.common.convertMeaningsToString
import com.geekbrains.translator.ui.common.isOnline
import com.geekbrains.translator.ui.view.adapters.MainAdapter
import com.geekbrains.translator.ui.view.pages.DescriptionActivity
import com.geekbrains.translator.ui.view.pages.SearchDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    override lateinit var viewModel: MainViewModel

    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(dataItem: DataModel) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        dataItem.text!!,
                        convertMeaningsToString(dataItem.meanings!!),
                        dataItem.meanings[0].imageUrl!!
                    )
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun renderData(appState: AppState) {
        when (appState) {

            is AppState.Success -> {
                showViewLoading()

                val dataModel = appState.data

                if (dataModel.isNullOrEmpty()) {
                    showErrorScreen(
                        getString(R.string.empty_server_response_on_success)
                    )
                } else {
                    showViewSuccess()
                    adapter!!.setData(dataModel)
                }
            }

            is AppState.Loading -> {
                showViewLoading()

                binding.progress.visibility =
                    if (appState.progress != null) {
                        VISIBLE
                    } else {
                        GONE
                    }
            }

            is AppState.Error -> {
                showErrorScreen(appState.error.message)
            }
        }
    }

    private fun initViewModel() {
        if (binding.translations.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }

        val _viewModel: MainViewModel by viewModel()
        viewModel = _viewModel
        viewModel.subscribe().observe(
            this@MainActivity, { renderData(it) }
        )
    }

    private fun initViews() {
        initSearchClickListener()

        binding.translations.layoutManager = LinearLayoutManager(applicationContext)
        binding.translations.adapter = adapter
    }

    private fun initSearchClickListener() {
        binding.openSearchDialogButton.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()

            searchDialogFragment.setOnSearchClickListener(
                object : SearchDialogFragment.OnSearchClickListener {
                    override fun onClick(word: String) {
                        isNetworkAvailable = isOnline(applicationContext)

                        if (isNetworkAvailable) {
                            viewModel.getData(word, isNetworkAvailable)
                        } else {
                            showViewError()
                        }
                    }
                }
            )
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()

        binding.error.text = error ?: getString(R.string.undefined_error)
        binding.reload.setOnClickListener {
            viewModel.getData("hi", true)
        }
    }

    private fun showViewSuccess() {
        binding.successGroup.visibility = VISIBLE
        binding.loadingGroup.visibility = GONE
        binding.errorGroup.visibility = GONE
    }

    private fun showViewLoading() {
        binding.successGroup.visibility = GONE
        binding.loadingGroup.visibility = VISIBLE
        binding.errorGroup.visibility = GONE
    }

    private fun showViewError() {
        binding.successGroup.visibility = GONE
        binding.loadingGroup.visibility = GONE
        binding.errorGroup.visibility = VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "b0956816-286e-11ee-be56-0242ac120002"
    }
}