package com.example.knowhub.data.injection

import com.example.knowhub.data.datasource.CatalogRemoteDataSource
import com.example.knowhub.data.datasource.impl.CatalogRemoteDataSourceImpl
import com.example.knowhub.data.datasource.services.AsignaturaRetrofitService
import com.example.knowhub.data.datasource.services.KnowHubApi
import com.example.knowhub.data.datasource.services.ReviewRetrofitService
import com.example.knowhub.data.datasource.services.UserRetrofitService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindCatalogRemoteDataSource(
        implementation: CatalogRemoteDataSourceImpl
    ): CatalogRemoteDataSource

    companion object {

        @Singleton
        @Provides
        fun providesRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun providesKnowHubApi(
            retrofit: Retrofit
        ): KnowHubApi {
            return retrofit.create(KnowHubApi::class.java)
        }

        @Singleton
        @Provides
        fun providesReviewRetrofitService(
            retrofit: Retrofit
        ): ReviewRetrofitService {
            return retrofit.create(ReviewRetrofitService::class.java)
        }

        @Singleton
        @Provides
        fun providesAsignaturaRetrofitService(
            retrofit: Retrofit
        ): AsignaturaRetrofitService {
            return retrofit.create(AsignaturaRetrofitService::class.java)
        }

        @Singleton
        @Provides
        fun providesUserRetrofitService(
            retrofit: Retrofit
        ): UserRetrofitService {
            return retrofit.create(UserRetrofitService::class.java)
        }
    }
}