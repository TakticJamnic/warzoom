package com.takticjamnic.warzoom.core

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.takticjamnic.warzoom.core.camera.CameraController
import com.takticjamnic.warzoom.core.world.WorldMap
import com.takticjamnic.warzoom.core.world.WorldRenderer
import kotlin.math.min

class WarZoom : ApplicationAdapter() {

    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var spriteBatch: SpriteBatch

    private lateinit var cameraController: CameraController
    private lateinit var worldRenderer: WorldRenderer

    private lateinit var worldMap: WorldMap

    private val tmpRect = com.badlogic.gdx.math.Rectangle()

    override fun create() {
        val mouseWorld = Vector3()
        camera = OrthographicCamera(
            Gdx.graphics.width.toFloat(),
            Gdx.graphics.height.toFloat()
        )

        camera.position.set(0f, 0f, 0f)
        camera.zoom = 1f
        camera.update()

        shapeRenderer = ShapeRenderer()
        spriteBatch = SpriteBatch()

        cameraController = CameraController(camera)

        worldMap = WorldMap.demoMap()

        worldRenderer = WorldRenderer(
            camera,
            shapeRenderer,
            spriteBatch
        )
    }

    override fun render() {

        val delta = Gdx.graphics.deltaTime

        cameraController.update(delta)

        Gdx.gl.glClearColor(0.08f, 0.08f, 0.08f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.LEFT)) {
            val mouse = camera.unproject(
                Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            )

            worldMap.selection.box.selecting = true
            worldMap.selection.box.start.set(mouse.x, mouse.y)
            worldMap.selection.box.end.set(mouse.x, mouse.y)
            select(Vector2(mouse.x, mouse.y), worldMap)
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT).not()
            && worldMap.selection.box.selecting
            && Gdx.input.isTouched.not()
        ) {
            finishSelection()
            worldMap.selection.box.selecting = false
        }

        if (Gdx.input.isButtonJustPressed(com.badlogic.gdx.Input.Buttons.RIGHT)) {
            val mouse = camera.unproject(
                Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            )

            move(Vector2(mouse.x, mouse.y), worldMap)
        }

        camera.update()

        update(delta)

        worldRenderer.render(worldMap)
    }

    private fun update(delta: Float) {
        if (worldMap.selection.box.selecting) {

            val m = camera.unproject(
                Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            )

            worldMap.selection.box.end.set(m.x, m.y)
        }

        worldMap.actors.forEach {
            it.update(delta)
        }
    }

    private fun finishSelection() {

        tmpRect.set(
            min(worldMap.selection.box.start.x, worldMap.selection.box.end.x),
            min(worldMap.selection.box.start.y, worldMap.selection.box.end.y),
            kotlin.math.abs(worldMap.selection.box.end.x - worldMap.selection.box.start.x),
            kotlin.math.abs(worldMap.selection.box.end.y - worldMap.selection.box.start.y)
        )

        worldMap.actors.forEach { actor ->
            if (tmpRect.contains(actor.position.x, actor.position.y)) {
                worldMap.selection.actors.add(actor)
            }
        }
    }

    override fun dispose() {
        shapeRenderer.dispose()
        spriteBatch.dispose()
    }

    private fun move(point: Vector2, worldMap: WorldMap) {
        worldMap.selection.actors.forEach { it.target = point }
    }

    private fun select(point: Vector2, worldMap: WorldMap) {
        worldMap.selection.reset()

        for (actor in worldMap.actors) {
            if (actor.contains(point, 20f)) {
                worldMap.selection.actors.add(actor)
                return
            }
        }

        for (building in worldMap.buildings) {
            if (building.contains(point, 20f)) {
                worldMap.selection.building = building
                return
            }
        }

        for (tree in worldMap.trees) {
            if (tree.contains(point, 20f)) {
                worldMap.selection.tree = tree
                return
            }
        }

        for (road in worldMap.roads) {
            if (road.contains(point, 20f)) {
                worldMap.selection.road = road
                return
            }
        }
    }
}