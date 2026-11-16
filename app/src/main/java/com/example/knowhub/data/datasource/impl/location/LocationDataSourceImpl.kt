package com.example.knowhub.data.datasource.impl.location

import android.content.Context
import android.location.Location
import com.example.knowhub.data.datasource.LocationDataSource
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : LocationDataSource {
    // Cliente de Google Play Services que solicita la ubicación del dispositivo.
    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    // Prefer a fresh fix, but use the cached fix if Play Services cannot produce one.
    override suspend fun getCurrentLocation(): Location? {
        val currentLocation = withTimeoutOrNull(12_000) {
            locationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                CancellationTokenSource().token
            ).await()
        }
        return currentLocation ?: locationClient.lastLocation.await()
    }
}
