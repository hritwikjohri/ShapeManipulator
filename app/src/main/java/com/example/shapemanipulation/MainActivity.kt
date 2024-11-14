package com.example.shapemanipulation

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var shapeView: ShapeView
    private lateinit var shapeDialog: ShapeCreationDialog
    private var createButtonText = "Create Shape"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        }

        shapeView = ShapeView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
            setBackgroundColor(Color.WHITE)
        }
        layout.addView(shapeView)

        shapeDialog = ShapeCreationDialog(this)

        Button(this).apply {
            text = createButtonText
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                bottomMargin = 16
            }
            setOnClickListener {
                shapeDialog.show { shape ->
                    shapeView.addShape(shape)
                }
            }
        }.also { layout.addView(it) }

        setContentView(layout)
    }

    override fun onPause() {
        super.onPause()
        shapeView.saveShapes()
    }

    override fun onStop() {
        super.onStop()
        shapeView.saveShapes()
    }

}