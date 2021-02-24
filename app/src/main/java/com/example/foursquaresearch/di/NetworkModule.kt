package com.example.foursquaresearch.di

import com.example.foursquaresearch.data.api.BASE_URL
import com.example.foursquaresearch.data.api.SearchApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    //
    // lazy dagger to improve the runtime opening the app speed:
    // https://www.zacsweers.dev/dagger-party-tricks-deferred-okhttp-init/
    //
    @Provides
    @Singleton
    fun provideRestaurantApi(client: dagger.Lazy<OkHttpClient>): SearchApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .callFactory { request -> client.get().newCall(request) }
            .baseUrl(BASE_URL)
            .build()
            .create(SearchApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }
}
