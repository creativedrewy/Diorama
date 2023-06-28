package com.creativedrewy.diorama.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.creativedrewy.diorama.rendering.DioramaTextureView
import com.creativedrewy.diorama.theme.DioramaTheme
import org.rajawali3d.view.ISurface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DioramaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        AndroidView(
                            modifier = Modifier
                                .width(300.dp)
                                .height(500.dp),
                            factory = { context ->
                                val textureView = DioramaTextureView(context)
                                textureView.setFrameRate(60.0)
                                textureView.renderMode = ISurface.RENDERMODE_CONTINUOUSLY

                                textureView
                            }
                        )
                    }
                }
            }
        }
    }
}