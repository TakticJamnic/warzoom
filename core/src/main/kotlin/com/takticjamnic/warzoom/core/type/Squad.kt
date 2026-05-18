package com.takticjamnic.warzoom.core.type

class Squad(

    val id: Int,

    val actors: MutableList<Actor> = mutableListOf()

) {

    var selected = false
}