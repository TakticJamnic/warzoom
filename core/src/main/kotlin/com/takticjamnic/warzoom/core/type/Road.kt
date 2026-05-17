package com.takticjamnic.warzoom.core.type

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.takticjamnic.warzoom.core.util.Calc

data class Road(
    val nodes: List<Node>,
    val width: Float,
    val color: Color
) : Selectable {
    override fun isSelected(point: Vector2, threshold: Float): Boolean {

        for (i in 0..<nodes.size - 1) {

            val a = nodes[i]
            val b = nodes[i + 1]

            if (Calc.pointToSegmentDistance(point.x, point.y, a.x, a.y, b.x, b.y, width, threshold)) {
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

