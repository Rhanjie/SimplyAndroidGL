package com.rhanjie.me.opengltest.drawable

import android.opengl.GLES31
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer : GLSurfaceView.Renderer {
    private lateinit var triangle: Triangle
    private lateinit var square: Square

    private lateinit var triangleCoords: FloatArray
    private lateinit var squareCoords: FloatArray

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        GLES31.glClearColor(0.2f, 0.5f, 1.0f, 1.0f)

        this.setCoords()
        triangleCoords = floatArrayOf(
            0.0f, 0.622008459f, 0.0f,   //top
            -0.5f, -0.311004243f, 0.0f, //bottom left
            0.5f, -0.311004243f, 0.0f   //bottom right
        )

        triangle = Triangle(triangleCoords)
        square = Square(squareCoords)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT)

        triangle.draw()
        //square.draw();
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES31.glViewport(0, 0, width, height)
    }

    companion object {
        fun loadShaderFromMemory(type: Int, shaderCode: String): Int {
            return GLES31.glCreateShader(type).also { shader ->
                GLES31.glShaderSource(shader, shaderCode)
                GLES31.glCompileShader(shader)
            }
        }
    }


    private fun setCoords() {
        triangleCoords = floatArrayOf(
            0.0f, 0.622008459f, 0.0f,   //top
            -0.5f, -0.311004243f, 0.0f, //bottom left
            0.5f, -0.311004243f, 0.0f   //bottom right
        )

        squareCoords = floatArrayOf(
            -0.5f,  0.5f, 0.0f,         //top left
            -0.5f, -0.5f, 0.0f,         //bottom left
            0.5f, -0.5f, 0.0f,          //bottom right
            0.5f,  0.5f, 0.0f           //top right
        )
    }
}
