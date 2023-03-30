package com.example.acronymsmeaningsapp.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.acronymsmeaningsapp.remote.Repository
import com.example.acronymsmeaningsapp.remote.RepositoryImpl
import com.example.acronymsmeaningsapp.service.ApiReference
import com.example.acronymsmeaningsapp.service.ServiceApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideOkHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    @Provides
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiReference.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun provideApiDetails(
        retrofit: Retrofit
    ): ServiceApi = retrofit.create(ServiceApi::class.java)


    @Provides
    fun provideRepository(
        apiDetails: ServiceApi,
    ): Repository =
        RepositoryImpl(apiDetails)

    @Provides
    fun provideConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}