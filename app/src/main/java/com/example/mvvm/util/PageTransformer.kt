package com.example.mvvm.util

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class PageTransformer(screenHeight: Int) : ViewPager2.PageTransformer {
    private val maxAlpha = 1.0f
    private val minAlpha = 0.8f

    private val maxScale = 1f
    private val scalePercent = 0.9f
    private val minScale = scalePercent * maxScale

    private val nextItemTranslationX = 2.5F * screenHeight / 60

    override fun transformPage(page: View, position: Float) {
        // position  -1: left, 0: center, 1: right
        val absPosition = kotlin.math.abs(position)
        // alpha from MIN_ALPHA to MAX_ALPHA
        page.alpha = maxAlpha - (maxAlpha - minAlpha) * absPosition
        // scale from MIN_SCALE to MAX_SCALE
        val scale = maxScale - (maxScale - minScale) * absPosition
        page.scaleY = scale
        page.scaleX = scale
        // translation X
        page.translationX = -position * nextItemTranslationX
    }
}