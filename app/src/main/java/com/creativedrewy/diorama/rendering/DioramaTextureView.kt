package com.creativedrewy.diorama.rendering

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import org.rajawali3d.materials.Material
import org.rajawali3d.materials.textures.ATexture.TextureException
import org.rajawali3d.materials.textures.Texture
import org.rajawali3d.primitives.Sphere
import org.rajawali3d.renderer.Renderer
import org.rajawali3d.view.TextureView

class DioramaTextureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextureView(context, attrs) {

    init {
        val renderer = MyRenderer(context)

        setSurfaceRenderer(renderer)
    }

}

class MyRenderer(
    context: Context
) : Renderer(context) {

    override fun initScene() {
//        try {
        val material = Material()
        material.colorInfluence = 1f
        material.color = Color.RED

        val sphere = Sphere(1f, 24, 24)
        sphere.material = material

        currentScene.addChild(sphere)
//        } catch (e: TextureException) {
//            e.printStackTrace()
//        }

        currentCamera.enableLookAt()
        currentCamera.setLookAt(0.0, 0.0, 0.0)
        currentCamera.setZ(6.0)
        currentCamera.orientation = currentCamera.orientation.inverse()
    }

    override fun onTouchEvent(event: MotionEvent?) {

    }

    override fun onOffsetsChanged(xOffset: Float, yOffset: Float, xOffsetStep: Float, yOffsetStep: Float, xPixelOffset: Int, yPixelOffset: Int) { }
}