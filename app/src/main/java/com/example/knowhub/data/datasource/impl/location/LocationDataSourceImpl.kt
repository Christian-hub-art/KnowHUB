package com.example.knowhub.data.datasource.impl.location

import android.content.Context
import android.location.Location
import com.example.knowhub.data.datasource.LocationDataSource
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : LocationDataSource {
    // Cliente de Google Play Services que solicita la ubicación del dispositivo.
    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    // Obtiene una ubicación aproximada de bajo consumo y espera el resultado de forma suspendible.
    override suspend fun getCurrentLocation(): Location? =
        locationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            CancellationTokenSource().token
        ).await()
}
