package com.example.knowhub.data.injection

import com.example.knowhub.data.datasource.ReviewRemoteDataSource
import com.example.knowhub.data.datasource.impl.retrofit.ReviewRetrofitDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindReviewRemoteDataSource(
        impl: ReviewRetrofitDataSourceImpl
    ): ReviewRemoteDataSource
}
