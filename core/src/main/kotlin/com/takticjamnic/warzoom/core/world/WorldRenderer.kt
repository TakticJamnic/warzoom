package com.takticjamnic.warzoom.core.world

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import kotlin.math.abs
import kotlin.math.min

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
        renderActors(worldMap)

        renderTrees(worldMap)


        shapeRenderer.end()

        renderSelectionBox(worldMap)
    }

    private fun renderBuildings(worldMap: WorldMap) {
        for (building in worldMap.buildings) {
            val selected = worldMap.selection.building?.let {
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
            val selected = worldMap.selection.road?.let {
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
            val selected = worldMap.selection.tree?.let {
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

    private fun renderActors(worldMap: WorldMap) {

        for (actor in worldMap.actors) {
            val selected = worldMap.selection.actors.contains(actor)
            shapeRenderer.color = if (selected)
                Color.YELLOW
            else
                actor.color

            shapeRenderer.circle(
                actor.position.x,
                actor.position.y,
                actor.radius
            )
        }
    }

    fun renderSelectionBox(worldMap: WorldMap) {
        if (!worldMap.selection.box.selecting) {
            return
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.YELLOW

        val x = min(worldMap.selection.box.start.x, worldMap.selection.box.end.x)
        val y = min(worldMap.selection.box.start.y, worldMap.selection.box.end.y)
        val w = abs(worldMap.selection.box.end.x - worldMap.selection.box.start.x)
        val h = abs(worldMap.selection.box.end.y - worldMap.selection.box.start.y)

        shapeRenderer.rect(x, y, w, h)
        shapeRenderer.end()
    }
}