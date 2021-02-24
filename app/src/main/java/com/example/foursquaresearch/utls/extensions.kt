package com.example.foursquaresearch.utls

import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.core.content.ContextCompat


var View.visibleOrGone: Boolean?
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value ?: return) View.VISIBLE else View.GONE
    }

fun Context.hasPermissions(permissions: Array<String>): Boolean {
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
    }

    return true
}
