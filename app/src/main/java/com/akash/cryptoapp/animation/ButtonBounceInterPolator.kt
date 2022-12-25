package com.akash.cryptoapp.animation

import android.view.animation.Interpolator

internal class ButtonBounceInterpolator(amplitude: Double, frequency : Double):
Interpolator{
    private var mAmplitude = 1.0
    private var mfrequency = 10.0

    override fun getInterpolation(time: Float): Float {
        return (-1 * Math.pow(Math.E, -time / mAmplitude)* Math.cos(mfrequency * time)+1).toFloat()
    }
    init {
        mAmplitude = amplitude
        mfrequency = frequency
    }
}