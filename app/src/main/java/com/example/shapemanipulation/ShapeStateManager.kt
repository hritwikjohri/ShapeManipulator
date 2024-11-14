package com.example.shapemanipulation

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import org.json.JSONObject

class ShapeStateManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "shape_preferences",
        Context.MODE_PRIVATE
    )

    fun saveShapes(shapes: List<Shape>) {
        val shapesArray = JSONArray()

        shapes.forEach { shape ->
            val shapeJson = JSONObject().apply {
                put("id", shape.id)
                put("x", shape.x)
                put("y", shape.y)
                put("rotation", shape.rotation)
                put("scale", shape.scale)
                put("color", shape.color)
                put("type", shape.type.name)
            }
            shapesArray.put(shapeJson)
        }

        sharedPreferences.edit()
            .putString(KEY_SHAPES, shapesArray.toString())
            .apply()
    }

    fun loadShapes(): List<Shape> {
        val shapesJson = sharedPreferences.getString(KEY_SHAPES, "[]")
        val shapesArray = JSONArray(shapesJson)
        val shapes = mutableListOf<Shape>()

        for (i in 0 until shapesArray.length()) {
            val shapeJson = shapesArray.getJSONObject(i)
            shapes.add(
                Shape(
                    id = shapeJson.getString("id"),
                    x = shapeJson.getDouble("x").toFloat(),
                    y = shapeJson.getDouble("y").toFloat(),
                    rotation = shapeJson.getDouble("rotation").toFloat(),
                    scale = shapeJson.getDouble("scale").toFloat(),
                    color = shapeJson.getInt("color"),
                    type = ShapeType.valueOf(shapeJson.getString("type"))
                )
            )
        }
        return shapes
    }

    companion object {
        private const val KEY_SHAPES = "saved_shapes"
    }
}