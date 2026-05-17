package com.takticjamnic.warzoom.core.type

import com.badlogic.gdx.math.Vector2

interface Selectable {
    fun isSelected(point: Vector2, threshold: Float): Boolean
}