@file:Suppress("MissingPermission")

package com.example.foursquaresearch.utls

import android.content.Context
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

/**
 *  LocationHelper: helping function related to locations (GPS, etc)
 */
class LocationHelper @Inject constructor(
    @ApplicationContext
    private val context: Context
) {

    suspend fun getUserLastLocation(): LatLng? {
        return suspendCoroutine { cont ->
            LocationServices.getFusedLocationProviderClient(context).lastLocation
                .addOnCompleteListener {
                    if (it.result == null) {
                        cont.resumeWith(Result.success(null))
                        return@addOnCompleteListener
                    }
                    val result = LatLng(
                        it.result.latitude.toString(),
                        it.result.longitude.toString()
                    )
                    cont.resumeWith(Result.success(result))
                }
                .addOnFailureListener {
                    cont.resumeWith(Result.success(null))
                }
        }
    }

    data class LatLng(
        val latitude: String,
        val longitude: String
    )

}
