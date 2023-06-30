package com.creativedrewy.diorama.datamodel

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