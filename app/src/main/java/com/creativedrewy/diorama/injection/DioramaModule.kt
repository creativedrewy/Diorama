package com.creativedrewy.diorama.injection

import com.creativedrewy.diorama.endpoints.HeliusEndpoints
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(
    ViewModelComponent::class
)
class DioramaModule {

    @Provides
    fun provideRetrofitHelius(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl("https://mainnet.helius-rpc.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(Json.asConverterFactory(contentType))
            .client(client)
            .build()
    }

    @Provides
    fun provideHeliusEndpoints(retrofit: Retrofit): HeliusEndpoints {
        return retrofit.create(HeliusEndpoints::class.java)
    }

}