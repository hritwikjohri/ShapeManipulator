package com.example.shapemanipulation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import kotlin.math.sqrt

class ShapeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val shapes = mutableListOf<Shape>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 30f
    }

    private var selectedShape: Shape? = null
    private var lastTouchX = 0f
    private var lastTouchY = 0f
    private var lastTapTime = 0L

    private val stateManager = ShapeStateManager(context)
    private val scaleGestureDetector = ScaleGestureDetector(context, ScaleListener())
    private val rotationGestureDetector = RotationGestureDetector(RotationListener())

    init {
        isClickable = true
        isFocusable = true
        loadShapes()
    }

    fun addShape(shape: Shape) {
        shapes.add(shape)
        saveShapes()
        invalidate()
    }

    private fun removeShape(shape: Shape) {
        shapes.remove(shape)
        saveShapes()
        invalidate()
    }

    fun saveShapes() {
        stateManager.saveShapes(shapes)
    }

    private fun loadShapes() {
        shapes.clear()
        shapes.addAll(stateManager.loadShapes())
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        shapes.forEach { shape ->
            canvas.save()
            canvas.translate(shape.x, shape.y)
            canvas.rotate(shape.rotation)
            canvas.scale(shape.scale, shape.scale)

            paint.color = shape.color

            when (shape.type) {
                ShapeType.CIRCLE -> {
                    canvas.drawCircle(0f, 0f, 70f, paint)
                }
                ShapeType.SQUARE -> {
                    canvas.drawRect(-70f, -70f, 70f, 70f, paint)
                }
            }

            canvas.restore()
            canvas.drawText(
                "X: ${shape.x.toInt()}, Y: ${shape.y.toInt()}",
                shape.x - 60f,
                shape.y - 80f,
                textPaint
            )
            canvas.drawText(
                "Rotation: ${shape.rotation.toInt()}°",
                shape.x - 60f,
                shape.y - 40f,
                textPaint
            )
            canvas.drawText(
                "Scale: %.2f".format(shape.scale),
                shape.x - 60f,
                shape.y - 0f,
                textPaint
            )
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        rotationGestureDetector.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val currentTime = System.currentTimeMillis()
                val shape = findShapeAtPosition(event.x, event.y)

                if (shape != null) {
                    if (currentTime - lastTapTime < 300) {
                        removeShape(shape)
                    } else {
                        selectedShape = shape
                        lastTouchX = event.x
                        lastTouchY = event.y
                    }
                }
                lastTapTime = currentTime
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 1 && selectedShape != null) {
                    val dx = event.x - lastTouchX
                    val dy = event.y - lastTouchY
                    selectedShape?.apply {
                        x += dx
                        y += dy
                    }
                    lastTouchX = event.x
                    lastTouchY = event.y
                    invalidate()
                }
                return true
            }

            MotionEvent.ACTION_UP -> {
                selectedShape = null
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun findShapeAtPosition(x: Float, y: Float): Shape? {
        return shapes.findLast { shape ->
            val dx = x - shape.x
            val dy = y - shape.y
            sqrt(dx * dx + dy * dy) <= 50f * shape.scale
        }
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            selectedShape?.let { shape ->
                shape.scale *= detector.scaleFactor
                shape.scale = shape.scale.coerceIn(0.5f, 2.0f)
                invalidate()
            }
            return true
        }
    }

    private inner class RotationListener : RotationGestureDetector.OnRotationGestureListener {
        override fun onRotation(rotationDetector: RotationGestureDetector): Boolean {
            selectedShape?.let { shape ->
                shape.rotation += rotationDetector.angle
                shape.rotation %= 360
                invalidate()
            }
            return true
        }
    }
}