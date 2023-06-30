package com.creativedrewy.diorama.datamodel

data class HeliusRequest(
    val jsonrpc: String,
    val id: String,
    val method: String,
    val params: RequestParams
)

data class RequestParams(
    val ownerAddress: String,
    val page: Number,
    val limit: Number
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