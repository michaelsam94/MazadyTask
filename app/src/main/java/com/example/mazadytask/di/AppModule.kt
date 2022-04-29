package com.example.mazadytask.di

import com.example.mazadytask.common.Constants
import com.example.mazadytask.data.remote.MazadyApi
import com.example.mazadytask.data.repository.MazadyRepositoryImpl
import com.example.mazadytask.domain.repository.MazadyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMazadyApi(): MazadyApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(MazadyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMazadyRepository(api: MazadyApi): MazadyRepository {
        return MazadyRepositoryImpl(api)
    }

}