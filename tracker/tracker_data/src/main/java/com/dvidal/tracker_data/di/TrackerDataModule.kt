package com.dvidal.tracker_data.di

import android.app.Application
import androidx.room.Room
import com.dvidal.tracker_data.local.TrackerDao
import com.dvidal.tracker_data.local.TrackerDatabase
import com.dvidal.tracker_data.remote.OpenFoodApi
import com.dvidal.tracker_data.repository.TrackerRepositoryImpl
import com.dvidal.tracker_domain.repository.TrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodApi(client: OkHttpClient): OpenFoodApi {
        return Retrofit.Builder()
            .baseUrl(OpenFoodApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application): TrackerDatabase {
        return Room.databaseBuilder(
            context = app,
            klass = TrackerDatabase::class.java,
            name = "tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrackerRepository(api: OpenFoodApi, database: TrackerDatabase): TrackerRepository {
        return TrackerRepositoryImpl(database.dao, api)
    }
}