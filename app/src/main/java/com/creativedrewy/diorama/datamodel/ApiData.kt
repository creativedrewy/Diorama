package com.creativedrewy.diorama.datamodel

data class HeliusRequest(
    val jsonrpc: String = "2.0",
    val id: String = "GetNfts",
    val method: String = "getAssetsByOwner",
    val params: RequestParams
)

data class RequestParams(
    val ownerAddress: String,
    val page: Number = 1,
    val limit: Number = 10
)

data class HeliusJsonRpcResult(
    val jsonrpc: String,
    val result: HeliusResult
)

data class HeliusResult(
    val total: Number,
    val limit: Number,
    val page: Number,
    val items: List<NftResult>
)

data class NftResult(
    val id: String,
    val content: Content
)

data class Content(
    val json_uri: String,
    val files: List<FileData>,
)

data class FileData(
    val uri: String,
    val mime: String
)