package com.genericandwildcard.coronafinder.app.core

import android.widget.Switch
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.*
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.genericandwildcard.coronafinder.app.R
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

    fun showSettingsBottomSheet(
        isPercentage: Boolean,
        percentageSwitchCallback: (isChecked: Boolean) -> Unit
    ) {
        activityProvider.activity?.let { activity ->
            val dialog = MaterialDialog(
                activity,
                BottomSheet()
            ).show {
                setPeekHeight(res = R.dimen.settingsHeight)
                lifecycleOwner(activity)
                customView(R.layout.settings, horizontalPadding = true, scrollable = true)
            }

            // Setup custom view content
            val customView = dialog.getCustomView()
            val percentageSwitch: Switch = customView.findViewById(R.id.percentage_switch)
            percentageSwitch.isChecked = isPercentage
            percentageSwitch.setOnCheckedChangeListener { switch, isChecked ->
                percentageSwitchCallback(isChecked)
            }
        }
    }
}
