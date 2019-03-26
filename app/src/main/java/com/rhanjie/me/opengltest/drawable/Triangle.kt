package com.rhanjie.me.opengltest.drawable

import android.opengl.GLES31
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

class Triangle(var coords: FloatArray) {
    private val vertexShaderCode = "" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "   gl_Position = vPosition;" +
            "}"

    private val fragmentShaderCode = "" +
            "precision mediump float;" +
            "uniform vec4 vColor; " +
            "void main() {" +
            "   gl_FragColor = vColor;" +
            "}"


    val vertices = 3
    val coordsPerVertex = 3
    val color = floatArrayOf(0.5f, 1.0f, 0.0f, 1.0f)

    private var program: Int

    private var positionHandle: Int = 0
    private var colorHandle: Int = 0

    private val vertexCount: Int = (vertices * coordsPerVertex) / coordsPerVertex
    private val vertexStride: Int = coordsPerVertex * 4

    init {
        val vertexShader: Int = MyGLRenderer.loadShaderFromMemory(GLES31.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = MyGLRenderer.loadShaderFromMemory(GLES31.GL_FRAGMENT_SHADER, fragmentShaderCode)

        program = GLES31.glCreateProgram().also {
            GLES31.glAttachShader(it, vertexShader)
            GLES31.glAttachShader(it, fragmentShader)
            GLES31.glLinkProgram(it)
        }
    }

    fun draw() {
        GLES31.glUseProgram(program)
        positionHandle = GLES31.glGetAttribLocation(program, "vPosition").also {
            GLES31.glEnableVertexAttribArray(it)
            GLES31.glVertexAttribPointer(it, coordsPerVertex, GLES31.GL_FLOAT, false, vertexStride, vertexBuffer)

            colorHandle = GLES31.glGetAttribLocation(program, "vColor").also {
                GLES31.glUniform4fv(colorHandle, 1, color, 0)
            }

            GLES31.glDrawArrays(GLES31.GL_TRIANGLES, 0, vertexCount)
            GLES31.glDisableVertexAttribArray(it)
        }

    }

    private var vertexBuffer: FloatBuffer = ByteBuffer.allocateDirect((vertices * coordsPerVertex) * 4).run {
        order(ByteOrder.nativeOrder())

        asFloatBuffer().apply {
            put(coords)
            position(0)
        }
    }
}