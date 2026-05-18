package com.takticjamnic.warzoom.core.type

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import kotlin.math.sqrt

class Actor(
    val position: Vector2,
    val radius: Float,
    val maxSpeed: Float,
    var rotation: Float,
    val rotationSpeed: Float,
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

    fun update(delta: Float, actors: List<Actor>) {
        val t = target ?: return

        val separation = Vector2()

        for (other in actors) {

            if (other === this) continue

            val dst = position.dst(other.position)

            val minDist = radius + other.radius

            if (dst < minDist && dst > 0.001f) {

                val push = Vector2(position)
                    .sub(other.position)
                    .nor()

                val strength = (minDist - dst) / minDist

                separation.mulAdd(push, strength)
            }
        }

        val dx = t.x - position.x
        val dy = t.y - position.y

        val dist = sqrt(dx * dx + dy * dy)

        if (dist < 2f) {
            target = null
            return
        }

        val targetAngle = Math.toDegrees(
            kotlin.math.atan2(dy.toDouble(), dx.toDouble())
        ).toFloat()

        rotation = rotateTowards(
            rotation,
            targetAngle,
            rotationSpeed * delta
        )

        val nx = dx / dist
        val ny = dy / dist

        val move = Vector2(nx, ny)

        move.add(separation.scl(0.7f))

        if (move.len2() > 0.0001f) {
            move.nor()
        }
        position.mulAdd(move, maxSpeed * delta)
//        position.x += nx * maxSpeed * delta
//        position.y += ny * maxSpeed * delta
    }

    private fun rotateTowards(
        current: Float,
        target: Float,
        maxDelta: Float
    ): Float {

        var delta = (target - current + 540f) % 360f - 180f

        delta = delta.coerceIn(-maxDelta, maxDelta)

        return (current + delta + 360f) % 360f
    }
}