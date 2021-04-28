package com.example.pokedex

import android.graphics.Color

object Utils {
// TODO:1 how to use it....
// val lighterColor: String = Utils.intColor2String(Utils.lighter(Color.parseColor("#FD7D24"), 0.4f))

    fun lighter(color: Int, factor: Float): Int {
        val red = ((Color.red(color) * (1 - factor) / 255 + factor) * 255).toInt()
        val green = ((Color.green(color) * (1 - factor) / 255 + factor) * 255).toInt()
        val blue = ((Color.blue(color) * (1 - factor) / 255 + factor) * 255).toInt()
        return Color.argb(Color.alpha(color), red, green, blue)
    }

    fun intColor2String(intColor: Int): String {
        return String.format("#%06X", 0xFFFFFF and intColor)
    }
}