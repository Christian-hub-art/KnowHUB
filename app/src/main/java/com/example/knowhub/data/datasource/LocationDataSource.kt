package com.example.knowhub.data.datasource

import android.location.Location

interface LocationDataSource {
    suspend fun getCurrentLocation(): Location?
}
