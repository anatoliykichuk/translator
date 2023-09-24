package com.geekbrains.core.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.geekbrains.core.R
import com.geekbrains.core.databinding.LoadingLayoutBinding
import com.geekbrains.core.domain.IInteractor
import com.geekbrains.model.AppState
import com.geekbrains.model.data.DataModel
import com.geekbrains.utils.ui.AlertDialogFragment
import com.geekbrains.utils.ui.OnlineLiveData

private const val DIALOG_FRAGMENT_TAG = "73762c04-fdda-4da4-b08d-62d563a1d352"

abstract class BaseActivity<T : AppState, I : IInteractor<T>> : AppCompatActivity() {

    private lateinit var binding: LoadingLayoutBinding
    abstract val viewModel: BaseViewModel<T>
    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        subscribeToNetworkChange()
    }

    override fun onResume() {
        super.onResume()

        binding = LoadingLayoutBinding.inflate(layoutInflater)

        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun renderData(appState: T) {
        when (appState) {

            is AppState.Success -> {
                showViewWorking()

                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_title_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }

            is AppState.Loading -> {
                showViewLoading()

                if (appState.progress != null) {
                    binding.progressBarHorizontal.visibility = View.VISIBLE
                    binding.progressBarRound.visibility = View.GONE
                    binding.progressBarHorizontal.progress = appState.progress!!
                } else {
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }

            is AppState.Error -> {
                showViewWorking()

                showAlertDialog(
                    getString(R.string.undefined_error),
                    appState.error.message
                )
            }
        }
    }

    private fun showViewWorking() {
        binding.loadingLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    private fun subscribeToNetworkChange() {
        OnlineLiveData(this).observe(
            this@BaseActivity,
            Observer<Boolean> {
                isNetworkAvailable = it

                if (!isNetworkAvailable) {
                    Toast.makeText(
                        this@BaseActivity,
                        R.string.dialog_message_device_is_offline,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }

    abstract fun setDataToAdapter(data: List<DataModel>)
}