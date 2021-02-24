package com.example.foursquaresearch.utls

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import javax.inject.Inject

/**
 *  Helper class related to permissions in Android.
 */
class AndroidPermissionHelper @Inject constructor() {

    private val callbackHolders = hashMapOf<RequestCodes, (Pair<(() -> Unit), (() -> Unit)>?)>()

    /**
     * check for location permission, if the permission is already given would call
     * permissionGranted, if user denies the permission, it would call permissionDenied.
     */
    fun checkPermissionLocation(
        activity: Activity,
        permissionGranted: (() -> Unit),
        permissionDenied: (() -> Unit)
    ) {
        val requestCode = RequestCodes.PermissionLocation
        checkPermission(
            activity,
            requestCode,
            permissionGranted,
            permissionDenied,
            getLocationPermissions()
        )
    }

    /**
     *  Needs to be called within activity.onRequestPermissionsResult
     */
    fun onRequestPermissionsResult(
        requestCodeAsInt: Int,
        grantResults: IntArray
    ) {
        val requestCode = RequestCodes.findByRawValue(requestCodeAsInt) ?: return
        val callback = callbackHolders[requestCode] ?: return

        if (grantResults.permissionGranted()) {
            callback.first.invoke()
        } else {
            callback.second.invoke()
        }
        callbackHolders[requestCode] = null
    }

    /**
     *  Should be called in activity.onDestroy, to clear callbacks, otherwise would cause memory
     *  leaks.
     */
    fun onDestroy() {
        callbackHolders.clear()
    }

    private fun getLocationPermissions() = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private fun checkPermission(
        activity: Activity,
        requestCode: RequestCodes,
        permissionGranted: (() -> Unit),
        permissionDenied: (() -> Unit),
        permissions: Array<String>
    ) {
        if (activity.hasPermissions(permissions)) {
            permissionGranted.invoke()
        } else {
            requestPermissions(
                activity,
                requestCode,
                permissionGranted,
                permissionDenied,
                permissions
            )
        }
    }

    private fun requestPermissions(
        activity: Activity,
        requestCode: RequestCodes,
        permissionGranted: (() -> Unit),
        permissionDenied: (() -> Unit),
        permissions: Array<String>
    ) {
        callbackHolders[requestCode] = Pair(permissionGranted, permissionDenied)
        ActivityCompat.requestPermissions(
            activity,
            permissions,
            requestCode.rawValue
        )
    }

    private fun IntArray.permissionGranted(): Boolean {
        return this.isNotEmpty() && this[0] == PackageManager.PERMISSION_GRANTED
    }


    enum class RequestCodes(val rawValue: Int) {
        PermissionLocation(1),
        ;

        companion object {
            fun findByRawValue(rawValue: Int): RequestCodes? {
                for (v in values()) {
                    if (v.rawValue == rawValue) {
                        return v
                    }
                }
                return null
            }
        }
    }

}
