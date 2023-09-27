package com.geekbrains.translator.ui.main

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.core.ui.BaseActivity
import com.geekbrains.model.AppState
import com.geekbrains.model.data.DataModel
import com.geekbrains.repository.convertMeaningsToString
import com.geekbrains.translator.R
import com.geekbrains.translator.databinding.ActivityMainBinding
import com.geekbrains.translator.domain.MainInteractor
import com.geekbrains.translator.ui.pages.SearchDialogFragment
import com.geekbrains.translator.ui.pages.description.DescriptionActivity
import com.geekbrains.translator.ui.pages.history.HistoryActivity
import com.geekbrains.utils.ui.viewById
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private lateinit var binding: ActivityMainBinding
    //override val viewModel: MainViewModel by inject()
    override val viewModel: MainViewModel by viewModel()
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val translationsView by viewById<RecyclerView>(R.id.translations_view)
    private val openSearchDialogButton by viewById<FloatingActionButton>(
        R.id.open_search_dialog_button
    )

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

        setDefaultSplashScreen()
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
        if (translationsView.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }

        viewModel.subscribe().observe(
            this@MainActivity, { renderData(it) }
        )
    }

    private fun initViews() {
        initSearchClickListener()

        translationsView.layoutManager = LinearLayoutManager(applicationContext)
        translationsView.adapter = adapter
    }

    private fun initSearchClickListener() {
        openSearchDialogButton.setOnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()

            searchDialogFragment.setOnSearchClickListener(
                object : SearchDialogFragment.OnSearchClickListener {
                    override fun onClick(word: String, fromRemoteSource: Boolean) {
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

    private fun setDefaultSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            setSplashScreenHideAnimation()
            setSplashScreenDuration()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setSplashScreenHideAnimation() {
        splashScreen.setOnExitAnimationListener {splashScreenView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.height.toFloat()
            )

            slideLeft.interpolator = AnticipateInterpolator()
            slideLeft.duration = SLIDE_LEFT_DURATION

            slideLeft.doOnEnd{splashScreenView.remove()}
            slideLeft.start()
        }
    }

    private fun setSplashScreenDuration() {
        var isHideSplashScreen = false

        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()

        val content: View = findViewById(android.R.id.content)

        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "b0956816-286e-11ee-be56-0242ac120002"
        private const val SLIDE_LEFT_DURATION = 1000L
        private const val COUNTDOWN_DURATION = 2000L
        private const val COUNTDOWN_INTERVAL = 1000L
    }
}