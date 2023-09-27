package com.geekbrains.translator.ui.pages.description

import android.content.Context
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.ImageLoader
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation
import com.geekbrains.translator.R
import com.geekbrains.translator.databinding.ActivityDescriptionBinding
import com.geekbrains.utils.ui.AlertDialogFragment
import com.geekbrains.utils.ui.OnlineLiveData

class DescriptionActivity : AppCompatActivity() {

    private var _binding: ActivityDescriptionBinding? = null
    private val binding
        get() = _binding!!

    @RequiresApi(31)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionbarHomeButtonAsUp()

        binding.descriptionSwipeRefresh.setOnRefreshListener {
            refreshData()
        }

        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (binding.descriptionSwipeRefresh.isRefreshing) {
            binding.descriptionSwipeRefresh.isRefreshing = false
        }
    }

    @RequiresApi(31)
    private fun setData() {
        val bundle = intent.extras

        binding.descriptionHeader.text = bundle?.getString(WORD_EXTRA)
        binding.description.text = bundle?.getString(DESCRIPTION_EXTRA)

        val imageLink = bundle?.getString(URL_EXTRA)

        if (imageLink.isNullOrEmpty()) {
            stopRefreshAnimationIfNeeded()
        } else {
            loadPhoto(binding.descriptionImage, imageLink)
        }
    }

    @RequiresApi(31)
    private fun refreshData() {
        OnlineLiveData(this).observe(
            this@DescriptionActivity,
            Observer<Boolean> {
                if (it) {
                    setData()
                } else {
                    AlertDialogFragment.newInstance(
                        getString(R.string.dialog_title_device_is_offline),
                        getString(R.string.dialog_message_device_is_offline)
                    ).show(
                        supportFragmentManager,
                        DIALOG_FRAGMENT_TAG
                    )
                    stopRefreshAnimationIfNeeded()
                }
            }
        )
    }

    @RequiresApi(31)
    private fun loadPhoto(imageView: ImageView, imageLink: String) {
        val request = LoadRequest.Builder(this)
            .data("https:$imageLink")
            .target(
                onStart = {},
                onSuccess = { result ->
                    imageView.setImageDrawable(result)

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val blurEffect = RenderEffect.createBlurEffect(
                            15f, 0f, Shader.TileMode.MIRROR
                        )
                        binding.root.setRenderEffect(blurEffect)
                    }
                },
                onError = { imageView.setImageResource(R.drawable.ic_error) }
            )
            .transformations(CircleCropTransformation())
            .build()

        ImageLoader(this).execute(request)
    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "ef1fa1dc-9b77-4874-bd80-5360ffafd3d2"
        private const val WORD_EXTRA = "5ad70bd2-53b3-4d98-b30a-31ad263f5ef3"
        private const val DESCRIPTION_EXTRA = "13c140fb-0643-42d6-8715-6508078afca1"
        private const val URL_EXTRA = "f56ee97f-bcde-4ae8-89a1-a099bb86d20d"

        fun getIntent(
            context: Context, word: String, description: String, url: String
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
        }
    }
}