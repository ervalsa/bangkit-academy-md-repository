package com.palsaloid.bluelock.customview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView(
    context: Context,
    attrs: AttributeSet
) : RecyclerView(context, attrs){

    fun <T : ViewHolder> initialize(newAdapter: Adapter<T>) {
        layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        newAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                post {
                    val sidePadding = (width / 2) - (getChildAt(0).width / 2)
                    setPadding(sidePadding, 0, sidePadding, 0)
                    scrollToPosition(1)
                    addOnScrollListener(object : OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            onScrollChanged()
                        }
                    })
                }
            }
        })
        adapter = newAdapter
    }

    private fun onScrollChanged() {
        post {
            (0 until childCount).forEach { position ->
                val child = getChildAt(position)
                val childCenterX = (child.left + child.right) / 2
                val scaleValue = getGaussianScale(childCenterX, 0.5f, 0.8f, 100.toDouble())
                child.scaleX = scaleValue
                child.scaleY = scaleValue
            }
        }
    }

    private fun getGaussianScale(
        childCenterX: Int,
        minScaleOffest: Float,
        scaleFactor: Float,
        spreadFactor: Double
    ): Float {
        val recyclerCenterX = (left + right) / 2
        return (Math.pow(Math.E,
            -Math.pow(childCenterX - recyclerCenterX.toDouble(),
                2.toDouble()) / (2 * Math.pow(
                    spreadFactor,
                    2.toDouble()
                ))
            ) * scaleFactor + minScaleOffest
        ).toFloat()
    }
}