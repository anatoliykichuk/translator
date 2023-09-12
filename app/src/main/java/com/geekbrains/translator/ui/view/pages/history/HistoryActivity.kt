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
    override val viewModel: HistoryViewModel by viewModel()
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

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun initViewModel() {
        if (binding.historyListLayout.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }

        viewModel.subscribe().observe(
            this@HistoryActivity, { renderData(it) }
        )
    }

    private fun initViews() {
        binding.historyListLayout.adapter = adapter
    }
}