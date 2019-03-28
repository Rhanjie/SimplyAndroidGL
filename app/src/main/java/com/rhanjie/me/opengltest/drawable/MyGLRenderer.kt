package com.rhanjie.me.opengltest.drawable

import android.opengl.GLES31
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer : GLSurfaceView.Renderer {
    private lateinit var triangle: Triangle
    private lateinit var square: Square

    private lateinit var triangleCoords: FloatArray
    private lateinit var squareCoords: FloatArray

    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val rotationMatrix = FloatArray(16)

    @Volatile
    var angle = 0f

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

        val scratch = FloatArray(16)

        //val time = SystemClock.uptimeMillis() % 4000L
        //val angle = 0.090f * time.toInt()
        Matrix.setRotateM(rotationMatrix, 0, angle, 0f, 0f, -1f)

        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, -3f, 0f, 0f, 0f, 0f, 1f, 0f)
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0)

        triangle.draw(scratch)

        //TODO: Finish square class
        //square.draw();
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES31.glViewport(0, 0, width, height)

        val ratio: Float = width.toFloat() / height.toFloat()
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f)
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
