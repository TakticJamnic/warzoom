package com.takticjamnic.warzoom.core.type

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import kotlin.math.sqrt

class Actor(
    val position: Vector2,
    val radius: Float,
    val maxSpeed: Float,
    val color: Color,
): Selectable {

    var target: Vector2? = null
    var speed: Float = 0f

    override fun contains(point: Vector2, threshold: Float): Boolean {
        val dx = point.x - position.x
        val dy = point.y - position.y
        val r = radius + threshold

        return dx * dx + dy * dy <= r * r
    }

    fun update(delta: Float) {
        val t = target ?: return

        val dx = t.x - position.x
        val dy = t.y - position.y

        val dist = sqrt(dx * dx + dy * dy)

        if (dist < 2f) {
            target = null
            return
        }

        val nx = dx / dist
        val ny = dy / dist

        position.x += nx * maxSpeed * delta
        position.y += ny * maxSpeed * delta
    }
}