package com.geekbrains.translator.ui.view.pages.history

import android.os.Bundle
import com.geekbrains.translator.data.model.DataModel
import com.geekbrains.translator.databinding.ActivityHistoryBinding
import com.geekbrains.translator.domain.inteactor.HistoryInteractor
import com.geekbrains.translator.ui.common.AppState
import com.geekbrains.translator.ui.common.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {

    private lateinit var binding: ActivityHistoryBinding
    override lateinit var viewModel: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData("", false)
    }

    override fun onDestroy() {
        super.onDestroy()
        //_binding = null
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun initViewModel() {
        if (binding.historyListLayout.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }

        val _viewModel: HistoryViewModel by viewModel()
        viewModel = _viewModel
        viewModel.subscribe().observe(
            this@HistoryActivity, { renderData(it) }
        )
    }

    private fun initViews() {
        binding.historyListLayout.adapter = adapter
    }
}