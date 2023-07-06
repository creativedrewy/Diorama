package com.creativedrewy.diorama.repository

import com.creativedrewy.diorama.BuildConfig
import com.creativedrewy.diorama.datamodel.HeliusRequest
import com.creativedrewy.diorama.datamodel.HeliusResult
import com.creativedrewy.diorama.datamodel.RequestParams
import com.creativedrewy.diorama.endpoints.HeliusEndpoints
import javax.inject.Inject


class HeliusApiRepository @Inject constructor(
    private val heliusEndpoints: HeliusEndpoints
) {

    suspend fun getAllAccountNfts(): HeliusResult {
        val requestBody = HeliusRequest(
            params = RequestParams(
                ownerAddress = "8nxM7WoVm77VYkMze7S1rNuv2adoVXuZu5VDrhQNWGnu"
            )
        )

        return heliusEndpoints.loadNfts(BuildConfig.HELIUS_KEY, requestBody).result
    }

}