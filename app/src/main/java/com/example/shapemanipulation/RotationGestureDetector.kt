package com.example.shapemanipulation

import android.view.MotionEvent
import kotlin.math.atan2

class RotationGestureDetector(private val mListener: OnRotationGestureListener) {
    private var mFocusX: Float = 0.toFloat()
    private var mFocusY: Float = 0.toFloat()
    private var mPrevAngle: Float = 0.toFloat()
    private var mCurrAngle: Float = 0.toFloat()

    var angle: Float = 0.toFloat()
        private set

    interface OnRotationGestureListener {
        fun onRotation(rotationDetector: RotationGestureDetector): Boolean
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (event.pointerCount == 2) {
                    mFocusX = event.getX(0) + event.getX(1)
                    mFocusY = event.getY(0) + event.getY(1)
                    mPrevAngle = calculateAngle(event)
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (event.pointerCount == 2) {
                    mCurrAngle = calculateAngle(event)
                    angle = mCurrAngle - mPrevAngle
                    if (mListener.onRotation(this)) {
                        mPrevAngle = mCurrAngle
                    }
                }
            }
        }
        return true
    }

    private fun calculateAngle(event: MotionEvent): Float {
        val dx = (event.getX(0) - event.getX(1)).toDouble()
        val dy = (event.getY(0) - event.getY(1)).toDouble()
        return Math.toDegrees(atan2(dy, dx)).toFloat()
    }
}