package com.kavya.campusconnect.di

import com.kavya.campusconnect.data.repository.EventRepositoryImpl
import com.kavya.campusconnect.data.repository.UserProfileRepositoryImpl
import com.kavya.campusconnect.domain.repository.EventRepository
import com.kavya.campusconnect.domain.repository.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindEventRepository(
        implementation: EventRepositoryImpl
    ): EventRepository


    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(
        implementation: UserProfileRepositoryImpl
    ): UserProfileRepository

}
