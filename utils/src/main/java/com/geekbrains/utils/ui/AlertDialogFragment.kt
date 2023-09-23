package com.geekbrains.utils.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import com.geekbrains.utils.getAlertDialog
import com.geekbrains.utils.getStubAlertDialog

class AlertDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity
        var alertDialog = getStubAlertDialog(context!!)

        arguments?.also {
            val title = it.getString(TITLE_EXTRA)
            val message = it.getString(MESSAGE_EXTRA)

            alertDialog = getAlertDialog(context, title, message)
        }
        return alertDialog
    }

    companion object {
        private const val TITLE_EXTRA = "4e8b5996-acb5-4632-9e8e-a10c5dd75cac"
        private const val MESSAGE_EXTRA = "aec508d7-5a9e-47f2-aad3-534dccace204"

        fun newInstance(title: String?, message: String?): AlertDialogFragment {
            val dialogFragment = AlertDialogFragment()

            dialogFragment.arguments = Bundle().apply {
                putString(TITLE_EXTRA, title)
                putString(MESSAGE_EXTRA, message)
            }
            return dialogFragment
        }
    }
}