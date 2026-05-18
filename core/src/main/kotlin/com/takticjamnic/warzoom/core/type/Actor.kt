package com.takticjamnic.warzoom.core.type

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.takticjamnic.warzoom.core.world.WorldMap
import kotlin.math.sqrt

class Actor(
    val position: Vector2,
    val radius: Float,
    val speed: Float,
    var rotation: Float,
    val rotationSpeed: Float,
    val color: Color,
    var squad: Squad? = null
): Selectable {
    val path = mutableListOf<Vector2>()

    override fun contains(point: Vector2, threshold: Float): Boolean {
        val dx = point.x - position.x
        val dy = point.y - position.y
        val r = radius + threshold

        return dx * dx + dy * dy <= r * r
    }

    fun update(delta: Float, worldMap: WorldMap) {
        val target = path.firstOrNull() ?: return

        val separation = Vector2()

        for (other in worldMap.actors) {

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

        val dx = target.x - position.x
        val dy = target.y - position.y

        val dist = sqrt(dx * dx + dy * dy)

        if (dist < 2f) {
            path.removeAt(0)
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
        val avoidance = Vector2()

        for (building in worldMap.buildings) {

            val closest = building.closestPoint(position)

            val dist = position.dst(closest)

            val avoidDistance = 32f

            if (dist < avoidDistance && dist > 0.001f) {

                val push = Vector2(position)
                    .sub(closest)
                    .nor()

                val strength =
                    (avoidDistance - dist) / avoidDistance

                avoidance.mulAdd(push, strength)
            }
        }

        for (tree in worldMap.trees) {

            val dist = position.dst(tree.position)

            val avoidDistance = tree.radius + 12f

            if (dist < avoidDistance && dist > 0.001f) {

                val push = Vector2(position)
                    .sub(tree.position)
                    .nor()

                val strength =
                    (avoidDistance - dist) / avoidDistance

                avoidance.mulAdd(push, strength)
            }
        }
        move.add(avoidance.scl(1.5f))
        move.add(separation.scl(0.7f))

        if (move.len2() > 0.001f) {
            move.nor()
        }
        position.mulAdd(move, speed * delta)
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