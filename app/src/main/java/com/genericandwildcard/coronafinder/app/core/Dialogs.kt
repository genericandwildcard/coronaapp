package com.genericandwildcard.coronafinder.app.core

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.genericandwildcard.coronafinder.app.core.definitions.ActivityProvider

class Dialogs(private val activityProvider: ActivityProvider) {

    fun showRetryDialog(message: String, retryCallback: () -> Unit) {
        activityProvider.activity?.let { activity ->
            MaterialDialog(activity).show {
                lifecycleOwner(activity)
                message(text = message)
                positiveButton { retryCallback.invoke() }
            }
        }
    }

    fun showRetryBottomSheet(message: String, retryCallback: () -> Unit) {
        activityProvider.activity?.let { activity ->
            MaterialDialog(
                activity,
                BottomSheet()
            ).show {
                lifecycleOwner(activity)
                message(text = message)
                positiveButton { retryCallback.invoke() }
            }
        }
    }
}
