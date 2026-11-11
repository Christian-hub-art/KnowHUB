package com.example.knowhub.data.datasource.repository

import android.util.Log
import com.example.knowhub.data.LocationCoordinates
import com.example.knowhub.data.datasource.impl.location.LocationDataSourceImpl
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val locationDataSource: LocationDataSourceImpl
) {
    suspend fun getCurrentLocation(): Result<LocationCoordinates> {
        return try {
            val location = locationDataSource.getCurrentLocation()
                ?: return Result.failure(IllegalStateException("No se obtuvo la ubicación"))
            Result.success(
                LocationCoordinates(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            )
        } catch (exception: Exception) {
            Log.e("LocationRepository", "No se pudo obtener la ubicación", exception)
            Result.failure(exception)
        }
    }
}
