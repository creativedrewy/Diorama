package com.creativedrewy.diorama.rendering

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import org.rajawali3d.Object3D
import org.rajawali3d.cameras.Camera2D
import org.rajawali3d.materials.Material
import org.rajawali3d.math.vector.Vector3
import org.rajawali3d.primitives.Plane
import org.rajawali3d.renderer.Renderer
import org.rajawali3d.util.ObjectColorPicker
import org.rajawali3d.util.OnObjectPickedListener
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

    val plane1 = Plane(200f, 200f, 1, 1)
    val plane2 = Plane(200f, 200f, 1, 1)

    val objectPicker = ObjectColorPicker(this)

    var selectedObj: Object3D? = null

    override fun initScene() {
        val material = Material()
        material.colorInfluence = 1f
        material.color = Color.RED

        plane1.material = material
        plane1.x = -300.0
        plane1.y = 300.0

        plane2.material = Material().apply {
            colorInfluence = 1f
            color = Color.GREEN
        }
        plane2.x = 300.0
        plane2.y = -300.0

        currentScene.addChild(plane1)
        currentScene.addChild(plane2)

        objectPicker.setOnObjectPickedListener(object: OnObjectPickedListener {
            override fun onObjectPicked(target: Object3D) {
                Log.v("Andrew", "You have picked an obj")
                selectedObj = target
            }

            override fun onNoObjectPicked() {
                Log.v("Andrew", "You have NOT picked an obj")
                selectedObj = null
            }
        })

        objectPicker.registerObject(plane1)
        objectPicker.registerObject(plane2)
    }

    override fun onRenderSurfaceSizeChanged(gl: GL10?, width: Int, height: Int) {
        super.onRenderSurfaceSizeChanged(gl, width, height)

        val camera2D = Camera2D()
        camera2D.width = width.toDouble()
        camera2D.height = height.toDouble()
        camera2D.setProjectionMatrix(0, 0)

        currentScene.switchCamera(camera2D)
    }

    private var startP1X = 0f
    private var startP1Y = 0f
    private var startP2X = 0f
    private var startP2Y = 0f

    private var prevEvtX = 0f
    private var prevEvtY = 0f
    private var prevEvtAngle = 0.0

    private var startScale = 0.0
    private var startPinchDist = 0.0

    override fun onTouchEvent(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startP1X = event.getX(0);
                startP1Y = event.getY(0);

                prevEvtX = startP1X
                prevEvtY = startP1Y

                objectPicker.getObjectAt(startP1X, startP1Y)
            }
            MotionEvent.ACTION_POINTER_2_DOWN -> {
                startP2X = event.getX(1);
                startP2Y = event.getY(1);

                val xDist = abs(startP1X - startP2X).toDouble()
                val yDist = abs(startP1Y - startP2Y).toDouble()

                startPinchDist = sqrt(xDist * xDist + yDist * yDist)
                selectedObj?.let {
                    startScale = it.scaleX

                    prevEvtAngle = it.rotX
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 1) {
                    val evtX = event.getX(0)
                    val evtY = event.getY(0)
                    val deltaX = evtX - prevEvtX
                    val deltaY = evtY - prevEvtY

                    selectedObj?.let {
                        it.x += deltaX
                        it.y += -deltaY
                    }

                    prevEvtX = evtX
                    prevEvtY = evtY
                } else if (event.pointerCount == 2) {
                    val currP1X = event.getX(0)
                    val currP1Y = event.getY(0)
                    val currP2X = event.getX(1)
                    val currP2Y = event.getY(1)

                    val angle = calculateAngleDelta(startP2X, startP2Y, startP1X, startP1Y, currP2X, currP2Y, currP1X, currP1Y)

                    val xDist = abs(currP1X - currP2X)
                    val yDist = abs(currP1Y - currP2Y)
                    val newDist = sqrt((xDist * xDist + yDist * yDist).toDouble())

                    selectedObj?.let {
                        it.rotate(Vector3.Axis.Z, -(angle - prevEvtAngle))
                        it.setScale(startScale * (newDist / startPinchDist))
                    }

                    prevEvtAngle = angle
                }
            }
            MotionEvent.ACTION_POINTER_2_UP -> {
                prevEvtX = event.getX(0);
                prevEvtY = event.getY(0);
            }
            MotionEvent.ACTION_POINTER_UP -> {
                prevEvtX = event.getX(1);
                prevEvtY = event.getY(1);
            }
        }
    }

    private fun calculateAngleDelta(
        startP1X: Float,
        startP1Y: Float,
        startP2X: Float,
        startP2Y: Float,
        deltaP1X: Float,
        deltaP1Y: Float,
        deltaP2X: Float,
        deltaP2Y: Float
    ): Double {
        val angle1 = atan2(startP1Y - startP2Y, startP1X - startP2X)
        val angle2 = atan2(deltaP1Y - deltaP2Y, deltaP1X - deltaP2X)
        var deltaAngle = Math.toDegrees((angle1 - angle2).toDouble()) % 360

        if (deltaAngle < -180f) {
            deltaAngle += 360.0f
        }

        if (deltaAngle > 180f) {
            deltaAngle -= 360.0f
        }

        return deltaAngle
    }

    override fun onOffsetsChanged(xOffset: Float, yOffset: Float, xOffsetStep: Float, yOffsetStep: Float, xPixelOffset: Int, yPixelOffset: Int) { }
}