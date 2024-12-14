package com.lazuka.emotioncalendar.di.modules

import com.lazuka.emotioncalendar.data.repository_impl.EventsRepositoryImpl
import com.lazuka.emotioncalendar.domain.repository.EventsRepository
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
    abstract fun bindEventsRepository(
        eventsRepositoryImpl: EventsRepositoryImpl
    ): EventsRepository
}