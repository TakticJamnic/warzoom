package com.takticjamnic.warzoom.core.map

import com.badlogic.gdx.graphics.Color
import com.takticjamnic.warzoom.core.type.Building
import com.takticjamnic.warzoom.core.type.Road
import com.takticjamnic.warzoom.core.type.Tree

data class WorldMap(
    val buildings: MutableList<Building> = mutableListOf(),
    val roads: MutableList<Road> = mutableListOf(),
    val trees: MutableList<Tree> = mutableListOf()
) {

    var selectedBuilding: Building? = null
    var selectedRoad: Road? = null

    companion object {

        fun demoMap(): WorldMap {

            val map = WorldMap()

            map.buildings += Building(
                x = -300f,
                y = -200f,
                width = 200f,
                height = 140f,
                color = Color.DARK_GRAY,
                rotation = 70f
            )

            map.buildings += Building(
                x = 100f,
                y = 100f,
                width = 120f,
                height = 220f,
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
                x = 250f,
                y = -300f,
                radius = 40f,
                color = Color.FOREST
            )

            return map
        }
    }
}