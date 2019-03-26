package com.rhanjie.me.opengltest

import android.content.Context
import android.opengl.GLSurfaceView
import com.rhanjie.me.opengltest.drawable.MyGLRenderer

class MyGLSurfaceView(context: Context): GLSurfaceView(context) {
    private val renderer: MyGLRenderer

    init {
        this.setEGLContextClientVersion(3)

        renderer = MyGLRenderer()
        this.setRenderer(renderer)
        renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
    }
}