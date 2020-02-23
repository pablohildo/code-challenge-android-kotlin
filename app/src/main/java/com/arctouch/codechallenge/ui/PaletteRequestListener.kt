package com.arctouch.codechallenge.ui

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_detail.*

class PaletteRequestListener(private val view: View) : RequestListener<Drawable?> {
    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
        return false
    }

    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
        if (resource is BitmapDrawable) {
            val palette = Palette.from(resource.bitmap).generate()
            val vibrant = palette.getVibrantColor(Color.WHITE)
            val alpha = if (vibrant != -1) vibrant else palette.getDominantColor(Color.WHITE)
            val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), Color.WHITE, alpha)
            colorAnimation.duration = 1
            colorAnimation.addUpdateListener { animator ->
                view.setBackgroundColor(animator.animatedValue as Int)
            }
            colorAnimation.start()
        }
        return false
    }
}