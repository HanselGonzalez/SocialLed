package com.sign.led.ui.createSigns

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentDialog
import com.sign.led.R
import com.sign.led.databinding.FragmentCreateSignsBinding


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
        initSpinners()

        binding.btnStyleTitle.setOnClickListener {
        showDialogTitle()
        }
    }

    private fun initSpinners() {

        val spinnerFont = dialogTitle.findViewById<Spinner>(R.id.spFont)
        val spFontItems = resources.getStringArray(R.array.spFontItems)

        val spinnerFontAdapter = ArrayAdapter(requireContext(), R.layout.spinner_selected, spFontItems)
        spinnerFontAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerFont.adapter = spinnerFontAdapter




        val spinnerAnimation = dialogTitle.findViewById<Spinner>(R.id.spAnimation)
        val spAnimationItems = resources.getStringArray(R.array.spAnimationItems)

        val spinnerAnimationAdapter = ArrayAdapter(requireContext(), R.layout.spinner_selected, spAnimationItems)
        spinnerAnimationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerAnimation.adapter = spinnerAnimationAdapter




        val spinnerSpeedAnimation = dialogTitle.findViewById<Spinner>(R.id.spSpeedAnimation)
        val spSpeedAnimationItems = resources.getStringArray(R.array.spSpeedAnimationItems)

        val spinnerSpeedAnimationAdapter = ArrayAdapter(requireContext(), R.layout.spinner_selected, spSpeedAnimationItems)
        spinnerSpeedAnimationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerSpeedAnimation.adapter = spinnerSpeedAnimationAdapter

    }

    private fun showDialogTitle() {
        val font = dialogTitle.findViewById<Spinner>(R.id.spFont)
        val animation = dialogTitle.findViewById<Spinner>(R.id.spAnimation)
        val speedAnimation = dialogTitle.findViewById<Spinner>(R.id.spSpeedAnimation)

        dialogTitle.show()










    }

    private fun initDialogs() {
        dialogTitle = Dialog(requireContext())
        dialogTitle.setContentView(R.layout.dialog_palette_colors)
        dialogTitle.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))




    }

}