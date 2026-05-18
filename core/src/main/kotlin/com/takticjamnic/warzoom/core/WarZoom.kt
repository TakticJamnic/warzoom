package com.takticjamnic.warzoom.core

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.takticjamnic.warzoom.core.camera.CameraController
import com.takticjamnic.warzoom.core.world.WorldController
import com.takticjamnic.warzoom.core.world.WorldMap
import com.takticjamnic.warzoom.core.world.WorldRenderer

class WarZoom : ApplicationAdapter() {

    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var spriteBatch: SpriteBatch

    private lateinit var cameraController: CameraController
    private lateinit var worldController: WorldController
    private lateinit var worldRenderer: WorldRenderer

    private lateinit var worldMap: WorldMap

    override fun create() {
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
        worldController = WorldController()
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
        worldController.updateClick(camera, delta, worldMap)

        camera.update()

        update(delta)

        worldRenderer.render(worldMap)
    }

    private fun update(delta: Float) {
        worldMap.actors.forEach {
            it.update(delta, worldMap)
        }
    }

    override fun dispose() {
        shapeRenderer.dispose()
        spriteBatch.dispose()
    }
}