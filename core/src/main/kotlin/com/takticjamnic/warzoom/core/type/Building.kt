package com.takticjamnic.warzoom.core.type

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.takticjamnic.warzoom.core.util.Calc

data class Building(
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val color: Color,
    val rotation: Float,
) : Selectable {
    override fun isSelected(point: Vector2, threshold: Float): Boolean {
        return Calc.isPointInRotatedBuilding(point.x, point.y, x, y, width, height, rotation, threshold)
    }
}