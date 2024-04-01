package com.sign.led.ui.signs.Adapter

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.recyclerview.widget.RecyclerView
import com.sign.led.databinding.ItemSignsProviderBinding
import com.sign.led.domain.model.ItemViewFullModelInfo

class SignsViewHolder(view: View) : RecyclerView.ViewHolder(view) {


    private val binding = ItemSignsProviderBinding.bind(view)


    fun render(signsList:ItemViewFullModelInfo, navigateToFullView:(Long) -> Unit){

        binding.tvNameSign.setText(signsList.name)


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
                        navigateToFullView(signsList.id)                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }
                })
            }


            binding.btnFullViewSigns.startAnimation(scaleAnimation)
        }

    }

}