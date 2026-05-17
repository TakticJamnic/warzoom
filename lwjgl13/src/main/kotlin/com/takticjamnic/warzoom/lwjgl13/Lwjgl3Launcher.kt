package com.takticjamnic.warzoom.lwjgl13

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.takticjamnic.warzoom.core.Wargame

object Lwjgl3Launcher {

    @JvmStatic
    fun main(args: Array<String>) {

        val config = Lwjgl3ApplicationConfiguration()

        config.setTitle("Wargame")
        config.setWindowedMode(1600, 900)
        config.useVsync(true)
        config.setForegroundFPS(144)

        Lwjgl3Application(Wargame(), config)
    }
}