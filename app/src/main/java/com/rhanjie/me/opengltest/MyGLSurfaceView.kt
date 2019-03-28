package com.rhanjie.me.opengltest

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent
import com.rhanjie.me.opengltest.drawable.MyGLRenderer

class MyGLSurfaceView(context: Context): GLSurfaceView(context) {
    private val renderer: MyGLRenderer

    private val touchScaleFactor: Float = 180f / 320f
    private var previousX = 0f
    private var previousY = 0f

    init {
        this.setEGLContextClientVersion(3)

        renderer = MyGLRenderer()
        this.setRenderer(renderer)

        //renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                var deltaX = x - previousX
                var deltaY = y - previousY

                if (y > height / 2)
                    deltaX *= -1

                if (x < width / 2)
                    deltaY *= -1

                renderer.angle += deltaX + deltaY
                this.requestRender()
            }
        }

        previousX = x
        previousY = y
        return true
    }
}