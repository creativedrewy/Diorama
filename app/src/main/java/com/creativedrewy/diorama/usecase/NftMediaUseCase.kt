package com.creativedrewy.diorama.usecase

import android.util.Log
import com.creativedrewy.diorama.repository.HeliusApiRepository
import javax.inject.Inject

data class NftMedia(
    val id: String,
    val uri: String,
    val mimeType: String
)

class NftMediaUseCase @Inject constructor(
    private val heliusApiRepository: HeliusApiRepository
) {

    suspend fun loadNftMedia(): List<NftMedia> {
        val result = heliusApiRepository.getAllAccountNfts()
        Log.v("Andrew", "Your result count: ${ result.total }")

        val nftMedia = mutableListOf<NftMedia>()

        result.items.forEach { item ->
            if (item.content.files.isNotEmpty()) {
                item.content.files.firstOrNull {
                    it.mime == "image/jpeg" || it.mime == "image/png"
                }?.let {
                    nftMedia.add(
                        NftMedia(
                            id = item.id,
                            uri = it.uri ?: "",
                            mimeType = it.mime ?: ""
                        )
                    )
                } //TODO: Get json metadata from uri and map to preview media
            }
        }

        return nftMedia
    }

}