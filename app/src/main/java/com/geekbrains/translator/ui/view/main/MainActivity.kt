package com.geekbrains.translator.ui.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.geekbrains.model.AppState
import com.geekbrains.model.data.DataModel
import com.geekbrains.translator.R
import com.geekbrains.translator.data.convertMeaningsToString
import com.geekbrains.translator.databinding.ActivityMainBinding
import com.geekbrains.translator.domain.inteactor.MainInteractor
import com.geekbrains.translator.ui.common.BaseActivity
import com.geekbrains.translator.ui.view.pages.SearchDialogFragment
import com.geekbrains.translator.ui.view.pages.description.DescriptionActivity
import com.geekbrains.translator.ui.view.pages.history.HistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private lateinit var binding: ActivityMainBinding
    override val viewModel: MainViewModel by viewModel()
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(dataItem: DataModel) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        dataItem.text!!,
                        convertMeaningsToString(dataItem.meanings!!),
                        dataItem.meanings!![0].imageUrl!!
                    )
                )
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.menu_history -> {
                startActivity(HistoryActivity.getIntent(this@MainActivity, ""))
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun initViewModel() {
        if (binding.translations.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }

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
                    override fun onClick(word: String, fromRemoteSource: Boolean) {
                        isNetworkAvailable = com.geekbrains.utils.isOnline(applicationContext)

                        if (!fromRemoteSource) {
                            showDataFromHistory(word)

                        } else if (isNetworkAvailable) {
                            viewModel.getData(word, isNetworkAvailable)

                        } else {
                            showNoInternetConnectionDialog()
                        }
                    }
                }
            )
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }
    }

    private fun showDataFromHistory(word: String) {
        startActivity(HistoryActivity.getIntent(this@MainActivity, word))
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "b0956816-286e-11ee-be56-0242ac120002"
    }
}