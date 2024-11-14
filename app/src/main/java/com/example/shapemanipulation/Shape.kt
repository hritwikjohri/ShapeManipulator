package com.example.shapemanipulation

import java.util.UUID

data class Shape(
    var id: String = UUID.randomUUID().toString(),
    var x: Float = 0f,
    var y: Float = 0f,
    var rotation: Float = 0f,
    var scale: Float = 1f,
    var color: Int,
    var type: ShapeType
)