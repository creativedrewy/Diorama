package com.creativedrewy.diorama.rendering

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import org.rajawali3d.cameras.Camera2D
import org.rajawali3d.cameras.OrthographicCamera
import org.rajawali3d.materials.Material
import org.rajawali3d.primitives.Plane
import org.rajawali3d.renderer.Renderer
import org.rajawali3d.view.TextureView
import javax.microedition.khronos.opengles.GL10

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
        val material = Material()
        material.colorInfluence = 1f
        material.color = Color.RED

        val plane = Plane(300f, 300f, 1, 1)
        plane.material = material

        currentScene.addChild(plane)
    }

    override fun onRenderSurfaceSizeChanged(gl: GL10?, width: Int, height: Int) {
        super.onRenderSurfaceSizeChanged(gl, width, height)

        Log.v("Andrew", "Your width: $width, height: $height")

        val orthoCamera = Camera2D()
        orthoCamera.width = width.toDouble()
        orthoCamera.height = height.toDouble()
        orthoCamera.setProjectionMatrix(0, 0)

        currentScene.switchCamera(orthoCamera)
    }

    override fun onTouchEvent(event: MotionEvent?) {

    }

    override fun onOffsetsChanged(xOffset: Float, yOffset: Float, xOffsetStep: Float, yOffsetStep: Float, xPixelOffset: Int, yPixelOffset: Int) { }
}