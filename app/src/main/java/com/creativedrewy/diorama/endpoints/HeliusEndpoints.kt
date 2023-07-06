package com.creativedrewy.diorama.endpoints

import com.creativedrewy.diorama.datamodel.HeliusJsonRpcResult
import com.creativedrewy.diorama.datamodel.HeliusRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface HeliusEndpoints {

    @POST("/")
    suspend fun loadNfts(@Query("api-key") key: String, @Body request: HeliusRequest): HeliusJsonRpcResult

}