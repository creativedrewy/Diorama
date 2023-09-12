package com.creativedrewy.diorama.usecase

import android.util.Log
import com.creativedrewy.diorama.BuildConfig
import com.creativedrewy.diorama.repository.HeliusApiRepository
import foundation.metaplex.readapi.GetAssetsByOwnerRpcInput
import foundation.metaplex.readapi.ReadApiDecorator
import foundation.metaplex.rpc.networking.NetworkDriver
import foundation.metaplex.solanapublickeys.PublicKey
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

        val rpcUrl = "https://mainnet.helius-rpc.com/?api-key=${BuildConfig.HELIUS_KEY}"

        val readApi = ReadApiDecorator(rpcUrl, NetworkDriver())

        repeat(5) {
            var assets = readApi.getAssetsByOwner(GetAssetsByOwnerRpcInput(PublicKey("8nxM7WoVm77VYkMze7S1rNuv2adoVXuZu5VDrhQNWGnu")))

            assets.items.forEach { item ->
                Log.v("Andrew", "Your item: ${item.content}")
            }
        }

//        val result = heliusApiRepository.getAllAccountNfts()
//        Log.v("Andrew", "Your result count: ${ result.total }")

        val nftMedia = mutableListOf<NftMedia>()

//        result.items.forEach { item ->
//            if (item.content.files.isNotEmpty()) {
//                item.content.files.firstOrNull {
//                    it.mime == "image/jpeg" || it.mime == "image/png"
//                }?.let {
//                    nftMedia.add(
//                        NftMedia(
//                            id = item.id,
//                            uri = it.uri ?: "",
//                            mimeType = it.mime ?: ""
//                        )
//                    )
//                } //TODO: Get json metadata from uri and map to preview media
//            }
//        }

        return nftMedia
    }

}