package com.geekbrains.translator.ui.view.pages.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.geekbrains.core.ui.BaseActivity
import com.geekbrains.model.AppState
import com.geekbrains.model.data.DataModel
import com.geekbrains.translator.databinding.ActivityHistoryBinding
import com.geekbrains.translator.domain.HistoryInteractor
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

        val bundle = intent.extras
        val word = with(bundle?.getString(WORD_EXTRA)) {
            if (this.isNullOrEmpty()) {
                ""
            } else {
                this
            }
        }

        viewModel.getData(word, false)
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

    companion object {
        private const val WORD_EXTRA = "6bd6013f-0430-47c1-beb5-b250554c586f"

        fun getIntent(
            context: Context, word: String
        ): Intent = Intent(context, HistoryActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
        }
    }
}