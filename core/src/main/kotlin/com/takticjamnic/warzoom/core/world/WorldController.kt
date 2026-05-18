package com.takticjamnic.warzoom.core.world

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import kotlin.math.min

class WorldController {

    val tmpRect = com.badlogic.gdx.math.Rectangle()

    fun updateClick(camera: OrthographicCamera, delta: Float, worldMap: WorldMap) {
        if (Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
            val mouse = camera.unproject(
                Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            )

            worldMap.selection.selecting = true
            worldMap.selection.start.set(mouse.x, mouse.y)
            worldMap.selection.end.set(mouse.x, mouse.y)
            select(Vector2(mouse.x, mouse.y), worldMap)
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT).not()
            && worldMap.selection.selecting
            && Gdx.input.isTouched.not()) {

            finishSelection(worldMap)
            worldMap.selection.selecting = false
        }

        if (Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.RIGHT)) {

            val mouse = camera.unproject(
                Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            )

            move(Vector2(mouse.x, mouse.y), worldMap)
        }

        if (worldMap.selection.selecting) {
            val m = camera.unproject(
                Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            )

            worldMap.selection.end.set(m.x, m.y)
        }
    }

    private fun finishSelection(worldMap: WorldMap) {
        tmpRect.set(
            min(worldMap.selection.start.x, worldMap.selection.end.x),
            min(worldMap.selection.start.y, worldMap.selection.end.y),
            kotlin.math.abs(worldMap.selection.end.x - worldMap.selection.start.x),
            kotlin.math.abs(worldMap.selection.end.y - worldMap.selection.start.y)
        )

        worldMap.actors.forEach { actor ->
            if (tmpRect.contains(actor.position.x, actor.position.y)) {
                worldMap.selected.actors.add(actor)
            }
        }
    }

    private fun move(point: Vector2, worldMap: WorldMap) {
        val actors = worldMap.selected.actors
        val center = Vector2()

        worldMap.selected.actors.forEach {
            center.add(it.position)
        }

        center.scl(1f / worldMap.selected.actors.size)

        val columns = kotlin.math.ceil(
            kotlin.math.sqrt(actors.size.toFloat()).toDouble()
        ).toInt()

        val forward = Vector2(
            point.x - center.x,
            point.y - center.y
        ).nor()

        val right = Vector2(
            -forward.y,
            forward.x
        )

        val spacing = 24f
        actors.forEachIndexed { index, actor ->

            val row = index / columns
            val col = index % columns

            val localX = (col - columns / 2f) * spacing
            val localY = -row * spacing

            val worldOffset = Vector2()

            worldOffset.mulAdd(right, localX)
            worldOffset.mulAdd(forward, localY)

            actor.target = Vector2(
                point.x + worldOffset.x,
                point.y + worldOffset.y
            )
        }
    }

    private fun select(point: Vector2, worldMap: WorldMap) {
        worldMap.selected.reset()

        for (actor in worldMap.actors) {
            if (actor.contains(point, 20f)) {
                worldMap.selected.actors.add(actor)
                return
            }
        }

        for (building in worldMap.buildings) {
            if (building.contains(point, 20f)) {
                worldMap.selected.building = building
                return
            }
        }

        for (tree in worldMap.trees) {
            if (tree.contains(point, 20f)) {
                worldMap.selected.tree = tree
                return
            }
        }

        for (road in worldMap.roads) {
            if (road.contains(point, 20f)) {
                worldMap.selected.road = road
                return
            }
        }
    }
}