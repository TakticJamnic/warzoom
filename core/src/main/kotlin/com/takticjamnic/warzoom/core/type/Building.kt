package com.takticjamnic.warzoom.core.type

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import kotlin.math.cos
import kotlin.math.sin

data class Building(
    val position: Vector2,
    val size: Vector2,
    val color: Color,
    val rotation: Float,
) : Selectable {

    val width = size.x
    val height = size.y

    override fun contains(point: Vector2, threshold: Float): Boolean {
        val cx = position.x + width / 2f
        val cy = position.y + height / 2f

        val dx = point.x - cx
        val dy = point.y - cy

        val rad = Math.toRadians(-rotation.toDouble())
        val cos = cos(rad)
        val sin = sin(rad)

        val localX = (dx * cos - dy * sin).toFloat()
        val localY = (dx * sin + dy * cos).toFloat()

        val halfW = width / 2f + threshold
        val halfH = height / 2f + threshold

        return localX in -halfW..halfW &&
                localY in -halfH..halfH
    }

    fun closestPoint(point: Vector2): Vector2 {

        val cx = point.x.coerceIn(position.x, position.x + width)
        val cy = point.y.coerceIn(position.y, position.y + height)

        return Vector2(cx, cy)
    }
}
