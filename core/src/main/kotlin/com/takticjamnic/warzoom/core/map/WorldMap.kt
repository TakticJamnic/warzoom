package com.takticjamnic.warzoom.core.map

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2
import com.takticjamnic.warzoom.core.type.Building
import com.takticjamnic.warzoom.core.type.Road
import com.takticjamnic.warzoom.core.type.Tree

data class WorldMap(
    val buildings: MutableList<Building> = mutableListOf(),
    val roads: MutableList<Road> = mutableListOf(),
    val trees: MutableList<Tree> = mutableListOf()
) {

    val selected = Selected()

    companion object {

        fun demoMap(): WorldMap {

            val map = WorldMap()

            map.buildings += Building(
                Vector2(-300f, -200f),
                Vector2(200f, 140f),
                color = Color.DARK_GRAY,
                rotation = 70f
            )

            map.buildings += Building(
                Vector2(100f, 100f),
                Vector2(120f, 220f),
                color = Color.BROWN,
                rotation = 60f
            )

            map.roads += Road(
                width = 30f,
                nodes = listOf(
                    Road.Node(-800f, 0f),
                    Road.Node(-400f, 100f),
                    Road.Node(0f, 0f),
                    Road.Node(400f, -150f),
                    Road.Node(800f, 0f)
                ),
                color = Color.DARK_GRAY,
            )

            map.trees += Tree(
                Vector2(250f, -300f),
                radius = 40f,
                color = Color.FOREST
            )

            return map
        }
    }

    class Selected {
        var building: Building? = null
        var road: Road? = null
        var tree: Tree? = null

        fun reset() {
            building = null
            road = null
            tree = null
        }

    }
}