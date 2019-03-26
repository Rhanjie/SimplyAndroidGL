package com.rhanjie.me.opengltest.drawable

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

//TODO: Not finished
class Square(var coords: FloatArray) {
    val vertices = 4
    val coordsPerVertex = 3

    val color = floatArrayOf(0.5f, 1.0f, 0.0f, 1.0f)
    private val drawOrder = shortArrayOf(0, 1, 2, 0, 2, 3)

    private var vertexBuffer: FloatBuffer = ByteBuffer.allocateDirect((vertices * coordsPerVertex) * 4).run {
        order(ByteOrder.nativeOrder())

        asFloatBuffer().apply {
            put(coords)
            position(0)
        }
    }

    private val drawListBuffer: ShortBuffer = ByteBuffer.allocateDirect(drawOrder.size * 2).run {
        order(ByteOrder.nativeOrder())

        asShortBuffer().apply {
            put(drawOrder)
            position(0)
        }
    }
}