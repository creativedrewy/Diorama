package com.creativedrewy.diorama.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.creativedrewy.diorama.rendering.DioramaTextureView
import com.creativedrewy.diorama.viewmodel.ConfiguratorViewModel
import org.rajawali3d.view.ISurface

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LandingScreen(
    viewmodel: ConfiguratorViewModel = hiltViewModel()
) {
    Box{
        val ctx = LocalContext.current
        val textureView = remember { DioramaTextureView(ctx) }

        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .pointerInteropFilter { event ->
                    textureView.onTouchEvent(event)
                    true
                },
            factory = { _ ->
                textureView.setFrameRate(60.0)
                textureView.renderMode = ISurface.RENDERMODE_CONTINUOUSLY

                textureView
            }
        )

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.BottomCenter),
        ) {

        }
    }
}