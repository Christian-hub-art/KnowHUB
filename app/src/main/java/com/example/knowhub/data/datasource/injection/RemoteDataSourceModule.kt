package com.example.knowhub.data.datasource.injection

import com.example.knowhub.data.datasource.UserRemoteDataSource
import com.example.knowhub.data.datasource.NotificationRemoteDataSource
import com.example.knowhub.data.datasource.impl.firestore.NotificationFirestoreDataSourceImpl
import com.example.knowhub.data.datasource.impl.firestore.UserFirestoreDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun bindUserRemoteDataSource(
        implementation: UserFirestoreDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    abstract fun bindNotificationRemoteDataSource(
        implementation: NotificationFirestoreDataSourceImpl
    ): NotificationRemoteDataSource
}
