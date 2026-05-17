package com.takticjamnic.warzoom.core.type

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

data class Road(
    val nodes: List<Node>,
    val width: Float,
    val color: Color
) : Selectable {
    override fun contains(point: Vector2, threshold: Float): Boolean {

        for (i in 0..<nodes.size - 1) {

            val a = nodes[i]
            val b = nodes[i + 1]

            val abx = b.x - a.x
            val aby = b.y - a.y

            val apx = point.x - a.x
            val apy = point.y - a.y

            val abLen2 = abx * abx + aby * aby

            val t = (apx * abx + apy * aby) / abLen2

            val clampedT = t.coerceIn(0f, 1f)

            val cx = a.x + abx * clampedT
            val cy = a.y + aby * clampedT

            val dx = point.x - cx
            val dy = point.y - cy

            val dist = kotlin.math.sqrt(dx * dx + dy * dy)

            if (dist < width + threshold) {
                return true
            }
        }

        return false
    }

    data class Node(
        val position: Vector2
    ) {
        val x = position.x
        val y = position.y

        constructor(x: Float, y: Float) : this(Vector2(x, y))
    }
}

