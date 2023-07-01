package com.creativedrewy.diorama.endpoints

import com.creativedrewy.diorama.datamodel.HeliusRequest
import com.creativedrewy.diorama.datamodel.HeliusResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HeliusEndpoints {

    @POST("/")
    suspend fun loadVideos(@Query("api-key") key: String, @Body request: HeliusRequest): HeliusResult

}