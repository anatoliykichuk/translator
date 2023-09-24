package com.geekbrains.translator.ui.pages

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.geekbrains.translator.databinding.FragmentSearchDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSearchDialogBinding? = null

    private val binding
        get() = _binding!!

    private var onSearchClickListener: OnSearchClickListener? = null

    private val textWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val searchOption = binding.searchOption.text

            if (searchOption != null && searchOption.toString().isNotEmpty()) {
                binding.startSearchFromNetByOptionButton.isEnabled = true
                binding.startSearchFromHistoryByOptionButton.isEnabled = true
                binding.clearSearchOptionButton.visibility = View.VISIBLE

            } else {
                binding.startSearchFromNetByOptionButton.isEnabled = false
                binding.startSearchFromHistoryByOptionButton.isEnabled = false
                binding.clearSearchOptionButton.visibility = View.GONE
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    private val onSearchFromNetByOptionClickListener = View.OnClickListener {
        onSearchClickListener?.onClick(binding.searchOption.text.toString(), true)
        dismiss()
    }

    private val onSearchFromHistoryByOptionClickListener = View.OnClickListener {
        onSearchClickListener?.onClick(binding.searchOption.text.toString(), false)
        dismiss()
    }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startSearchFromNetByOptionButton.setOnClickListener(
            onSearchFromNetByOptionClickListener
        )

        binding.startSearchFromHistoryByOptionButton.setOnClickListener(
            onSearchFromHistoryByOptionClickListener
        )

        binding.searchOption.addTextChangedListener(textWatcher)
        addOnClearClickListener()
    }

    private fun addOnClearClickListener() {
        binding.clearSearchOptionButton.setOnClickListener {
            binding.searchOption.setText("")
            binding.startSearchFromNetByOptionButton.isEnabled = false
            binding.startSearchFromHistoryByOptionButton.isEnabled = false
        }
    }

    interface OnSearchClickListener {
        fun onClick(word: String, fromRemoteSource: Boolean)
    }

    companion object {
        fun newInstance() = SearchDialogFragment()
    }
}