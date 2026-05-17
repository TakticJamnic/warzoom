package com.takticjamnic.warzoom.core.util

import kotlin.math.cos
import kotlin.math.sin

class Calc {

    companion object {
        fun pointToSegmentDistance(
            px: Float,
            py: Float,
            ax: Float,
            ay: Float,
            bx: Float,
            by: Float,
            width: Float,
            threshold: Float
        ): Boolean {

            val abx = bx - ax
            val aby = by - ay

            val apx = px - ax
            val apy = py - ay

            val abLen2 = abx * abx + aby * aby

            val t = (apx * abx + apy * aby) / abLen2

            val clampedT = t.coerceIn(0f, 1f)

            val cx = ax + abx * clampedT
            val cy = ay + aby * clampedT

            val dx = px - cx
            val dy = py - cy

            val dist =  kotlin.math.sqrt(dx * dx + dy * dy)

            return dist < width + threshold
        }

        fun isPointInRotatedBuilding(
            px: Float,
            py: Float,
            bx: Float,
            by: Float,
            width: Float,
            height: Float,
            rotationDeg: Float,
            threshold: Float
        ): Boolean {

            // 1. środek budynku
            val cx = bx + width / 2f
            val cy = by + height / 2f

            // 2. przesunięcie punktu do środka
            val dx = px - cx
            val dy = py - cy

            // 3. odwrócenie rotacji
            val rad = Math.toRadians(-rotationDeg.toDouble())
            val cos = cos(rad)
            val sin = sin(rad)

            val localX = (dx * cos - dy * sin).toFloat()
            val localY = (dx * sin + dy * cos).toFloat()

            // 4. sprawdzenie w lokalnym AABB
            // threshold jako "padding"
            val halfW = width / 2f + threshold
            val halfH = height / 2f + threshold

            return localX in -halfW..halfW &&
                    localY in -halfH..halfH
        }
    }
}