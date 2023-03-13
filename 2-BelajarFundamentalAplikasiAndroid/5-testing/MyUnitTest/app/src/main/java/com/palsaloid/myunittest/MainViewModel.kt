package com.palsaloid.myunittest

import androidx.lifecycle.ViewModel

class MainViewModel(private val cuboidModel: CuboidModel) : ViewModel() {

    fun getCircumference() = cuboidModel.getCircumference()

    fun getSurfaceArea() = cuboidModel.getSurfaceArea()

    fun getVolume() = cuboidModel.getVolume()

    fun save(w: Double, l: Double, h: Double) {
        cuboidModel.save(w, l, h)
    }
}