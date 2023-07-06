package com.creativedrewy.diorama.injection

import com.creativedrewy.diorama.endpoints.HeliusEndpoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//
//        val client: OkHttpClient = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()

        return Retrofit.Builder()
            .baseUrl("https://mainnet.helius-rpc.com/")
            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
            .build()
    }

    @Provides
    fun provideHeliusEndpoints(retrofit: Retrofit): HeliusEndpoints {
        return retrofit.create(HeliusEndpoints::class.java)
    }

}