package com.creativedrewy.diorama.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class NftPreview(
    val id: String,
    val uri: String,
    val type: String = ""
)

data class ViewState(
    val nftPreviews: List<NftPreview> = listOf()
)

@HiltViewModel
class ConfiguratorViewModel @Inject constructor(): ViewModel() {

    private val _configViewState = MutableStateFlow(ViewState())

    val configViewState = _configViewState.asStateFlow()

    init {
        _configViewState.update {
            ViewState(
                (0..10).map {
                    NftPreview(
                        id = "1",
                        uri = "https://cdn.pixabay.com/animation/2023/06/21/12/57/12-57-08-601_512.gif"
                    )
                }
            )
        }
    }
}