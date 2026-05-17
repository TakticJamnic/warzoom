package com.takticjamnic.warzoom.core.camera

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import kotlin.math.max
import kotlin.math.min

class CameraController(
    private val camera: OrthographicCamera
) : InputAdapter() {

    private val moveSpeed = 800f

    private var zoomDelta = 0f

    init {
        Gdx.input.inputProcessor = this
    }

    fun update(delta: Float) {

        val speed = moveSpeed * camera.zoom

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.y += speed * delta
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.y -= speed * delta
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.x -= speed * delta
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.x += speed * delta
        }

        if (zoomDelta != 0f) {

            camera.zoom += zoomDelta * 0.1f * camera.zoom

            camera.zoom = min(
                max(camera.zoom, 0.1f),
                100f
            )

            zoomDelta = 0f
        }
    }

    override fun scrolled(
        amountX: Float,
        amountY: Float
    ): Boolean {

        zoomDelta = amountY

        return true
    }
}