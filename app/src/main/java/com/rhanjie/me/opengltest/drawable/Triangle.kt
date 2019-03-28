package com.rhanjie.me.opengltest.drawable

import android.opengl.GLES31
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import kotlin.random.Random

class Triangle(var coords: FloatArray) {
    private val vertexShaderCode = "" +
            "uniform mat4 uMVPMatrix;" +
            "attribute vec4 vPosition;" +
            "void main() {" +
            "   gl_Position = uMVPMatrix * vPosition;" +
            "}"

    private val fragmentShaderCode = "" +
            "precision mediump float;" +
            "uniform vec4 vColor; " +
            "void main() {" +
            "   gl_FragColor = vColor;" +
            "}"


    val vertices = 3
    val coordsPerVertex = 3
    var color: FloatArray
        private set

    private var program: Int

    private var positionHandle: Int = 0
    private var colorHandle: Int = 0
    private var vPMatrixHandle: Int = 0

    private val vertexCount: Int = (vertices * coordsPerVertex) / coordsPerVertex
    private val vertexStride: Int = coordsPerVertex * 4

    init {
        color = floatArrayOf(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1.0f)

        val vertexShader: Int = MyGLRenderer.loadShaderFromMemory(GLES31.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = MyGLRenderer.loadShaderFromMemory(GLES31.GL_FRAGMENT_SHADER, fragmentShaderCode)

        program = GLES31.glCreateProgram().also {
            GLES31.glAttachShader(it, vertexShader)
            GLES31.glAttachShader(it, fragmentShader)
            GLES31.glLinkProgram(it)
        }
    }

    fun draw(mvpMatrix: FloatArray) {
        GLES31.glUseProgram(program)

        positionHandle = GLES31.glGetAttribLocation(program, "vPosition").also {
            GLES31.glEnableVertexAttribArray(positionHandle)
            GLES31.glVertexAttribPointer(positionHandle, coordsPerVertex, GLES31.GL_FLOAT, false, vertexStride, vertexBuffer)

            colorHandle = GLES31.glGetUniformLocation(program, "vColor").also {
                GLES31.glUniform4fv(colorHandle, 1, color, 0)
            }

            vPMatrixHandle = GLES31.glGetUniformLocation(program, "uMVPMatrix").also {
                GLES31.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)
            }

            GLES31.glDrawArrays(GLES31.GL_TRIANGLES, 0, vertexCount)
            GLES31.glDisableVertexAttribArray(positionHandle)
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