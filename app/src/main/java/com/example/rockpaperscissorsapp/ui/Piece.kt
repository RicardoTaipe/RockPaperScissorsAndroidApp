package com.example.rockpaperscissorsapp.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.example.rockpaperscissorsapp.R
import com.google.android.material.imageview.ShapeableImageView

class Piece : ShapeableImageView {
    enum class Shape(val icon: Int, val background: Int) {
        ROCK(R.drawable.icon_rock, R.drawable.bg_rock),
        PAPER(R.drawable.icon_paper, R.drawable.bg_paper),
        SCISSORS(R.drawable.icon_scissors, R.drawable.bg_scissors)
    }

//    var type: Type = Type.PAPER
//        set(value) {
//            field = value
//            // Set image drawable based on type
//            setImageResource(getImageDrawable(value))
//            // Set background drawable based on type
//            background = getBackgroundDrawable(value)
//        }


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.Piece, defStyle, R.style.Widget_Theme_RockPaperScissorsApp_Piece
        )
        val choice = a.getInt(R.styleable.Piece_shape, 0)
        a.recycle()
        val shape = Shape.values()[choice]
        setImageResource(shape.icon)
        background = ContextCompat.getDrawable(context, shape.background)
    }

}