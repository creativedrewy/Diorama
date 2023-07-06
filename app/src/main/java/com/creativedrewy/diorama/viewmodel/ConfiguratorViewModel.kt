package com.creativedrewy.diorama.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creativedrewy.diorama.repository.HeliusApiRepository
import com.creativedrewy.diorama.usecase.NftMediaUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NftPreview(
    val id: String,
    val uri: String
)

data class ViewState(
    val nftPreviews: List<NftPreview> = listOf()
)

@HiltViewModel
class ConfiguratorViewModel @Inject constructor(
    private val nftMediaUseCase: NftMediaUseCase
): ViewModel() {

    private val _configViewState = MutableStateFlow(ViewState())

    val configViewState = _configViewState.asStateFlow()

    init {
        viewModelScope.launch {
            val media = nftMediaUseCase.loadNftMedia()

            val previews = media.map {
                NftPreview(
                    id = it.id,
                    uri = it.uri,
                )
            }

            _configViewState.update {
                ViewState(previews)
            }
        }
    }
}