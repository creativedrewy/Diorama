package com.creativedrewy.diorama.rendering

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import org.rajawali3d.cameras.Camera2D
import org.rajawali3d.materials.Material
import org.rajawali3d.math.vector.Vector3
import org.rajawali3d.primitives.Plane
import org.rajawali3d.renderer.Renderer
import org.rajawali3d.view.TextureView
import javax.microedition.khronos.opengles.GL10
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.sqrt

class DioramaTextureView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : TextureView(context, attrs) {

    val renderer = MyRenderer(context)

    init {
        setSurfaceRenderer(renderer)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        renderer.onTouchEvent(event)

        return super.onTouchEvent(event)
    }

}

class MyRenderer(
    context: Context
) : Renderer(context) {

    val plane = Plane(300f, 300f, 1, 1)

    override fun initScene() {
        val material = Material()
        material.colorInfluence = 1f
        material.color = Color.RED

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

    private var previousX = 0f
    private var previousY = 0f
    private var previousRot = 0.0

    private var fX = 0f
    private var fY: Float = 0f
    private var sX: Float = 0f
    private var sY: Float = 0f
    private var angle = 0f
    private var lastAngle = 0f
    private var startScale = 0.0
    private var startDist = 0.0

    override fun onTouchEvent(event: MotionEvent) {
        val x: Float = event.x
        val y: Float = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                sX = event.getX(0);
                sY = event.getY(0);
            }
            MotionEvent.ACTION_POINTER_2_DOWN -> {
                fX = event.getX(1);
                fY = event.getY(1);

                val xDist = abs(sX - fX)
                val yDist = abs(sY - fY)
                startDist = Math.sqrt((xDist * xDist + yDist * yDist).toDouble())
                startScale = plane.scaleX

                lastAngle = plane.rotX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 1) {
                    val dx: Float = x - previousX
                    val dy: Float = y - previousY

//                    plane.x += dx
//                    plane.y += -dy
                } else if (event.pointerCount == 2) {
                    val nsX: Float = event.getX(0)
                    val nsY: Float = event.getY(0)
                    val nfX: Float = event.getX(1)
                    val nfY: Float = event.getY(1)

                    angle = angleBetweenLines(fX, fY, sX, sY, nfX, nfY, nsX, nsY)

                    val xDist = abs(nsX - nfX)
                    val yDist = abs(nsY - nfY)
                    val newDist = sqrt((xDist * xDist + yDist * yDist).toDouble())

                    plane.rotate(Vector3.Axis.Z, -(angle - lastAngle).toDouble())

                    plane.setScale(startScale * (newDist / startDist))

                    lastAngle = angle
                }
            }
            MotionEvent.ACTION_UP -> {
//                ptrID1 = INVALID_POINTER_ID
                lastAngle = 0f
            }
            MotionEvent.ACTION_POINTER_2_UP -> {
//                ptrID2 = INVALID_POINTER_ID
            }
            MotionEvent.ACTION_CANCEL -> {
//                ptrID1 = INVALID_POINTER_ID
//                ptrID2 = INVALID_POINTER_ID
            }
        }

        previousX = x
        previousY = y
    }

    private fun angleBetweenLines(fX: Float, fY: Float, sX: Float, sY: Float, nfX: Float, nfY: Float, nsX: Float, nsY: Float): Float {
        val angle1 = atan2((fY - sY).toDouble(), (fX - sX).toDouble()).toFloat()
        val angle2 = atan2((nfY - nsY).toDouble(), (nfX - nsX).toDouble()).toFloat()
        var angle = Math.toDegrees((angle1 - angle2).toDouble()).toFloat() % 360

        if (angle < -180f) angle += 360.0f
        if (angle > 180f) angle -= 360.0f

        return angle
    }

    override fun onOffsetsChanged(xOffset: Float, yOffset: Float, xOffsetStep: Float, yOffsetStep: Float, xPixelOffset: Int, yPixelOffset: Int) { }
}