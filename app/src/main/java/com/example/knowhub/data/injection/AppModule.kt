package com.example.knowhub.data.injection

import com.example.knowhub.data.datasource.CatalogRemoteDataSource
import com.example.knowhub.data.datasource.impl.CatalogRemoteDataSourceImpl
import com.example.knowhub.data.datasource.services.KnowHubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideKnowHubApi(retrofit: Retrofit): KnowHubApi = retrofit.create(KnowHubApi::class.java)

    @Singleton
    @Provides
    fun provideCatalogRemoteDataSource(api: KnowHubApi): CatalogRemoteDataSource = CatalogRemoteDataSourceImpl(api)
}
