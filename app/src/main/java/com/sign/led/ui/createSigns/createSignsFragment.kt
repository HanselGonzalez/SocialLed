package com.sign.led.ui.createSigns

import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.sign.led.R
import com.sign.led.databinding.FragmentCreateSignsBinding
import com.sign.led.domain.model.SpinnerFontModel
import com.sign.led.ui.createSigns.adapter.SpinnerFontAdapter


class createSignsFragment : Fragment() {


    private var _binding:FragmentCreateSignsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialogTitle: Dialog
    private lateinit var dialogBackground: Dialog



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateSignsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        initDialogs()

        binding.btnStyleTitle.setOnClickListener {
        showDialogTitle()
        }
    }

    private fun showDialogTitle() {

        val spinnerFont = dialogTitle.findViewById<Spinner>(R.id.spFont)
        val spinnerAnimation = dialogTitle.findViewById<Spinner>(R.id.spAnimation)
        val spinnerSpeedAnimation = dialogTitle.findViewById<Spinner>(R.id.spSpeedAnimation)



        //SPINNER FONT
        val spFontItems = resources.getStringArray(R.array.spFontItems)


        val customTypeface1 = Typeface.createFromAsset(requireContext().assets, "dmsan.ttf")
        val customTypeface2 = Typeface.createFromAsset(requireContext().assets, "leaguegothic.ttf")
        val customTypeface3 = Typeface.createFromAsset(requireContext().assets, "dmseriftext.ttf")
        val customTypeface4 = Typeface.createFromAsset(requireContext().assets, "fugazone.ttf")
        val customTypeface5 = Typeface.createFromAsset(requireContext().assets, "sixtyfour.ttf")
        val customTypeface6 = Typeface.createFromAsset(requireContext().assets, "pressstart.ttf")
        val customTypeface7 = Typeface.createFromAsset(requireContext().assets, "bungeeshade.ttf")
        val customTypeface8 = Typeface.createFromAsset(requireContext().assets, "amatic.ttf")



        val options = mutableListOf<SpinnerFontModel>()

        for ((index, text) in spFontItems.withIndex()) {
            val font: Typeface = when (index) {
                0 -> customTypeface1
                1 -> customTypeface2
                2-> customTypeface3
                3-> customTypeface4
                4-> customTypeface5
                5-> customTypeface6
                6-> customTypeface7
                7-> customTypeface8
                else -> customTypeface1
            }

            options.add(SpinnerFontModel(text, font))
        }


        val spinnerFontAdapter = SpinnerFontAdapter(requireContext(),options)
        spinnerFont.adapter = spinnerFontAdapter



        //SPINNER ANIMATION
        val spAnimationItems = resources.getStringArray(R.array.spAnimationItems)

        val spinnerAnimationAdapter = ArrayAdapter(requireContext(), R.layout.spinner_selected, spAnimationItems)
        spinnerAnimationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerAnimation.adapter = spinnerAnimationAdapter



        val spSpeedAnimationItems = resources.getStringArray(R.array.spSpeedAnimationItems)

        val spinnerSpeedAnimationAdapter = ArrayAdapter(requireContext(), R.layout.spinner_selected, spSpeedAnimationItems)
        spinnerSpeedAnimationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerSpeedAnimation.adapter = spinnerSpeedAnimationAdapter







        dialogTitle.show()

    }

    private fun initDialogs() {
        dialogTitle = Dialog(requireContext())
        dialogTitle.setContentView(R.layout.dialog_palette_colors)
        dialogTitle.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))




    }

}