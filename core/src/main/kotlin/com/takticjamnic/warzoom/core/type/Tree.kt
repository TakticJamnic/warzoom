package com.takticjamnic.warzoom.core.type

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

data class Tree(
    val position: Vector2,
    val radius: Float,
    val color: Color
) : Selectable {

    override fun contains(point: Vector2, threshold: Float): Boolean {
        val dx = point.x - position.x
        val dy = point.y - position.y
        val r = radius + threshold

        return dx * dx + dy * dy <= r * r
    }
}
