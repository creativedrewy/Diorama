package com.creativedrewy.diorama.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.creativedrewy.diorama.rendering.DioramaTextureView
import org.rajawali3d.view.ISurface

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LandingScreen(

) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        val ctx = LocalContext.current
        val textureView = remember { DioramaTextureView(ctx) }

        AndroidView(
            modifier = Modifier
                .width(300.dp)
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
    }
}