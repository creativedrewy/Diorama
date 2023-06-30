package com.creativedrewy.diorama.injection

import com.creativedrewy.diorama.BuildConfig
import com.creativedrewy.diorama.endpoints.HeliusEndpoints
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(
    ViewModelComponent::class
)
class DioramaModule {

    @Provides
    fun provideRetrofitHelius(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://mainnet.helius-rpc.com/?api-key=${ BuildConfig.HELIUS_KEY }")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideHeliusEndpoints(retrofit: Retrofit): HeliusEndpoints {
        return retrofit.create(HeliusEndpoints::class.java)
    }

}