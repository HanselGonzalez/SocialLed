package com.sign.led.ui.mysigns.Adapter

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.sign.led.databinding.ItemSignsBinding
import com.sign.led.domain.model.ItemViewFullModel

class MySignsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSignsBinding.bind(view)


    fun render(
        itemsFull: ItemViewFullModel,
        onDeleteClick: (ItemViewFullModel) -> Unit,
        navigateToFullView: (Long) -> Unit
    ) {


        binding.tvNameSign.text = itemsFull.name



        binding.btnFullViewSigns.setOnClickListener {
            val scaleAnimation = ScaleAnimation(
                1f, 1.2f,
                1f, 1.2f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
            ).apply {
                duration = 100
                interpolator = AccelerateDecelerateInterpolator()
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        navigateToFullView(itemsFull.id!!)                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }
                })
            }

            binding.btnFullViewSigns.startAnimation(scaleAnimation)
        }


        binding.btnSignDelete.setOnClickListener {
            val scaleAnimation = ScaleAnimation(
                1f, 1.2f,
                1f, 1.2f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
            ).apply {
                duration = 100
                interpolator = AccelerateDecelerateInterpolator()
                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        onDeleteClick(itemsFull)
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }
                })
            }

            binding.btnSignDelete.startAnimation(scaleAnimation)
        }


    }
}