package com.example.trafficlightsystem

import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.trafficlightsystem.databinding.FragmentSecondScreenBinding
import kotlinx.coroutines.*


class SecondScreen : Fragment() {
    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val RED_DELAY: Long = 4000L
        const val GREEN_DELAY: Long = 4000L
        const val ORANGE_DELAY: Long = 1000L
    }

    private lateinit var redLight: ImageView
    private lateinit var orangeLight: ImageView
    private lateinit var greenLight: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val carName = arguments?.let { SecondScreenArgs.fromBundle(it).carArgument }
        binding.carNameTxt.text = "Car model: $carName"

        redLight = binding.redCircleImg
        orangeLight = binding.orangeCircleImg
        greenLight = binding.greenCircleImg

        val switchLight = binding.switchLight

        switchLight.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    switchLight.text = switchLight.textOn
                    switchLight.showText

           GlobalScope.launch(Dispatchers.Main) {
                        makeLightActive(greenLight, R.color.green)
                        animateImage(greenLight, GREEN_DELAY)
                        delay(GREEN_DELAY)

                        makeLightActive(orangeLight, R.color.orange)
                        animateImage(orangeLight, ORANGE_DELAY)
                        delay(ORANGE_DELAY)

                        makeLightActive(redLight, R.color.red)
                        animateImage(redLight, RED_DELAY)
                        delay(RED_DELAY)
                    }
                } else {
                    switchLight.text = switchLight.textOff
                    switchLight.showText
                    disableTrafficLights()
                }
            }
      }

    private fun makeLightActive(img: ImageView, color: Int) =
        img.drawable.setColorFilter(
            ResourcesCompat.getColor(
                resources,
                color,
                null
            ), PorterDuff.Mode.ADD
        )

    fun makeLightInactive(img: ImageView) =
        img.drawable.setColorFilter(
            ResourcesCompat.getColor(
                resources,
                R.color.inactive_light,
                null
            ), PorterDuff.Mode.ADD
        )

    private fun disableTrafficLights() {
        makeLightInactive(redLight)
        makeLightInactive(orangeLight)
        makeLightInactive(greenLight)
    }

    private fun animateImage(img: ImageView, delay: Long) {
        val fadeIn = AlphaAnimation(0f, 1f)
        val fadeOut = AlphaAnimation(1f, 0f)
        val set = AnimationSet(false)

        set.addAnimation(fadeIn)
        set.addAnimation(fadeOut)
        set.duration = delay
        img.startAnimation(set)

        set.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {
            }
            override fun onAnimationEnd(animation: Animation) {
                makeLightInactive(img)
            }
        })
    }
}