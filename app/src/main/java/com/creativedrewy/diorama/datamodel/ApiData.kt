package com.creativedrewy.diorama.datamodel

import kotlinx.serialization.Serializable

data class HeliusRequest(
    val jsonrpc: String = "2.0",
    val id: String = "GetNfts",
    val method: String = "getAssetsByOwner",
    val params: RequestParams
)

@Serializable
data class RequestParams(
    val ownerAddress: String,
    val page: Number = 1,
    val limit: Number = 10
)

@Serializable
data class HeliusJsonRpcResult(
    val jsonrpc: String,
    val result: HeliusResult
)

@Serializable
data class HeliusResult(
    val total: Number,
    val limit: Number,
    val page: Number,
    val items: List<NftResult>
)

@Serializable
data class NftResult(
    val id: String,
    val content: Content
)

@Serializable
data class Content(
    val json_uri: String,
    val files: List<FileData>,
)

@Serializable
data class FileData(
    val uri: String?,
    val mime: String?
)