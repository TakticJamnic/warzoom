package com.takticjamnic.warzoom.core

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.takticjamnic.warzoom.core.camera.CameraController
import com.takticjamnic.warzoom.core.map.WorldMap
import com.takticjamnic.warzoom.core.renderer.WorldRenderer

class Wargame : ApplicationAdapter() {

    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var spriteBatch: SpriteBatch

    private lateinit var cameraController: CameraController
    private lateinit var worldRenderer: WorldRenderer

    private lateinit var worldMap: WorldMap


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

        if (Gdx.input.justTouched()) {

            val mouse = camera.unproject(
                Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            )

            select(mouse.x, mouse.y, worldMap)
        }

        camera.update()

        worldRenderer.render(worldMap)
    }

    override fun dispose() {
        shapeRenderer.dispose()
        spriteBatch.dispose()
    }

    private fun select(
        mx: Float,
        my: Float,
        worldMap: WorldMap
    ) {

        worldMap.selectedBuilding = null
        worldMap.selectedRoad = null

        for (building in worldMap.buildings) {
            if (building.isSelected(Vector2(mx, my), 20f)) {
                worldMap.selectedBuilding = building
                return
            }
        }

        for (road in worldMap.roads) {
            if (road.isSelected(Vector2(mx, my), 20f)) {
                worldMap.selectedRoad = road
                return
            }
        }
    }
}