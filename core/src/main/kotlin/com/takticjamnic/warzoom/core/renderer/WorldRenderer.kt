package com.takticjamnic.warzoom.core.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.takticjamnic.warzoom.core.map.WorldMap

class WorldRenderer(
    private val camera: OrthographicCamera,
    private val shapeRenderer: ShapeRenderer,
    private val spriteBatch: SpriteBatch
) {

    fun render(worldMap: WorldMap) {

        shapeRenderer.projectionMatrix = camera.combined

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)

        renderBuildings(worldMap)

        renderRoads(worldMap)

        renderTrees(worldMap)

        shapeRenderer.end()
    }

    private fun renderBuildings(worldMap: WorldMap) {
        for (building in worldMap.buildings) {
            val selected = worldMap.selected.building?.let {
                it == building
            } ?: false

            shapeRenderer.color = if (selected) {
                Color.YELLOW
            } else {
                building.color
            }

            shapeRenderer.rect(
                building.position.x,
                building.position.y,
                building.width /2,
                building.height /2,
                building.width,
                        building.height,
                1f,
                1f,
                building.rotation

            )
        }
    }

    private fun renderRoads(worldMap: WorldMap) {
        for (road in worldMap.roads) {
            val pts = road.nodes
            val selected = worldMap.selected.road?.let {
                it == road
            } ?: false

            shapeRenderer.color = if (selected) {
                Color.YELLOW
            } else {
                Color.GRAY
            }

            for (i in 0..<pts.size - 1) {
                shapeRenderer.rectLine(
                    pts[i].position,
                    pts[i + 1].position,
                    road.width
                )
            }
        }
    }

    private fun renderTrees(worldMap: WorldMap) {
        for (tree in worldMap.trees) {
            val selected = worldMap.selected.tree?.let {
                it == tree
            } ?: false

            shapeRenderer.color = if (selected) {
                Color.YELLOW
            } else {
                tree.color
            }

            shapeRenderer.circle(
                tree.position.x,
                tree.position.y,
                tree.radius
            )
        }
    }
}