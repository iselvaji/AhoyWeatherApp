package com.ahoy.weatherapp.utils

import android.app.Dialog
import android.content.Context
import android.util.Log
import com.ahoy.weatherapp.R

object UiUtils {

    private var progressDialog: Dialog? = null

    fun showDialogWithOKCancelButton(context: Context,
                                     messageToDisplay : String,
                                     positiveAction: (() -> Unit)? = null) {
        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(context)
        alertDialog.apply {
            setMessage(messageToDisplay)
            setPositiveButton(context.getString(android.R.string.ok)) { _, _ ->
                positiveAction?.invoke()
            }
            setNegativeButton(context.getString(android.R.string.cancel)) { _, _ ->
                //dismiss the dialog
            }
        }.create().show()
    }

    fun showProgressDialog(context: Context?, show: Boolean) {
        if (show) {
            if (!(progressDialog != null && progressDialog?.isShowing == true)) {
                context?.let {
                    progressDialog = Dialog(it)
                }
                progressDialog?.setCancelable(false)
                progressDialog?.setContentView(R.layout.progress_bar_layout)
                try {
                    progressDialog?.show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            try {
                progressDialog?.let {
                    if (it.isShowing) {
                        it.dismiss()
                    }
                }
            } catch (e: IllegalArgumentException) {
                Log.e("Error", "Exception while showing progress dialog")
            } finally {
                progressDialog = null
            }
        }
    }
}