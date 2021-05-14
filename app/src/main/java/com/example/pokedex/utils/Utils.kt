package com.example.pokedex.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

object Utils {
    fun hideKeyboard(activity: Activity) {
        val inputManager: InputMethodManager = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val currentFocusedView = activity.currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}