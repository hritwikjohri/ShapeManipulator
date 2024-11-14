package com.example.shapemanipulation

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import java.util.Locale

class ShapeCreationDialog(private val context: Context) {
    private var selectedColor: Int = Color.BLUE
    private var selectedType: ShapeType = ShapeType.CIRCLE
    private var shapeTypeButton = "Select Shape Type:"
    private var colorButton = "Select Color"

    private val colors = arrayOf(
        Color.BLUE to "Blue",
        Color.RED to "Red",
        Color.GREEN to "Green",
        Color.YELLOW to "Yellow",
        Color.MAGENTA to "Magenta"
    )

    fun show(onShapeCreated: (Shape) -> Unit) {
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 30, 50, 30)
        }

        layout.addView(TextView(context).apply {
            text = shapeTypeButton
            textSize = 18f
            setPadding(0, 0, 0, 16)
        })

        val radioGroup = RadioGroup(context).apply {
            orientation = RadioGroup.VERTICAL
            setOnCheckedChangeListener { group, checkedId ->
                group.findViewById<RadioButton>(checkedId)?.let { radioButton ->
                    selectedType = radioButton.tag as ShapeType
                }
            }
        }

        ShapeType.entries.forEach { shapeType ->
            RadioButton(context).apply {
                text = shapeType.name.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
                }
                id = View.generateViewId()
                tag = shapeType
                isChecked = shapeType == selectedType
                radioGroup.addView(this)
            }
        }
        layout.addView(radioGroup)

        layout.addView(TextView(context).apply {
            text = colorButton
            textSize = 18f
            setPadding(0, 24, 0, 16)
        })

        val colorButtonsLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }

        colors.forEach { (color, _) ->
            val colorButton = View(context).apply {
                layoutParams = LinearLayout.LayoutParams(60, 60).apply {
                    setMargins(8, 8, 8, 8)
                }
                setBackgroundColor(color)
                setOnClickListener {
                    selectedColor = color
                    colorButtonsLayout.children().forEach { view ->
                        view.elevation = if (view == this) 8f else 0f
                    }
                }
            }
            colorButtonsLayout.addView(colorButton)
        }
        layout.addView(colorButtonsLayout)

        AlertDialog.Builder(context)
            .setTitle("Create New Shape")
            .setView(layout)
            .setPositiveButton("Create") { _, _ ->
                val shape = Shape(
                    x = 300f,
                    y = 300f,
                    color = selectedColor,
                    type = selectedType
                )
                onShapeCreated(shape)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

private fun LinearLayout.children(): Sequence<View> = sequence {
    for (i in 0 until childCount) {
        yield(getChildAt(i))
    }
}