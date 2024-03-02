package com.sign.led.ui.createSigns

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
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

        val btnAddText = dialogTitle.findViewById<ImageButton>(R.id.btnCheck)
        val cvViewPreview = binding.cvViewPreview
        val etText = binding.etText


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


        val cvColor1 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor1)
        val cvColor2 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor2)
        val cvColor3 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor3)
        val cvColor4 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor4)
        val cvColor5 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor5)
        val cvColor6 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor6)
        val cvColor7 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor7)
        val cvColor8 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor8)
        val cvColor9 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor9)
        val cvColor10 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor10)
        val cvColor11 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor11)
        val cvColor12 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor12)
        val cvColor13 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor13)
        val cvColor14 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor14)
        val cvColor15 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor15)
        val cvColor16 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor16)
        val cvColor17 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor17)
        val cvColor18 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor18)
        val cvColor19 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor19)
        val cvColor20 = dialogTitle.findViewById<MaterialCardView>(R.id.cvColor20)

        val allCards = listOf(cvColor1,cvColor2,
                cvColor3,
                cvColor4,
                cvColor5,
                cvColor6,
                cvColor7,
                cvColor8,
                cvColor9,
                cvColor10,
                cvColor11,
                cvColor12,
                cvColor13,
                cvColor14,
                cvColor15,
                cvColor16,
                cvColor17,
                cvColor18,
                cvColor19,
                cvColor20)

        //DEFAULT
        handleCardSelection(cvColor1,allCards)

        cvColor1.setOnClickListener{handleCardSelection(cvColor1, allCards)}
        cvColor2.setOnClickListener{handleCardSelection(cvColor2, allCards)}
        cvColor3.setOnClickListener{handleCardSelection(cvColor3, allCards)}
        cvColor4.setOnClickListener{handleCardSelection(cvColor4, allCards)}
        cvColor5.setOnClickListener{handleCardSelection(cvColor5, allCards)}
        cvColor6.setOnClickListener{handleCardSelection(cvColor6, allCards)}
        cvColor7.setOnClickListener{handleCardSelection(cvColor7, allCards)}
        cvColor8.setOnClickListener{handleCardSelection(cvColor8, allCards)}
        cvColor9.setOnClickListener{handleCardSelection(cvColor9, allCards)}
        cvColor10.setOnClickListener{handleCardSelection(cvColor10, allCards)}
        cvColor11.setOnClickListener{handleCardSelection(cvColor11, allCards)}
        cvColor12.setOnClickListener{handleCardSelection(cvColor12, allCards)}
        cvColor13.setOnClickListener{handleCardSelection(cvColor13, allCards)}
        cvColor14.setOnClickListener{handleCardSelection(cvColor14, allCards)}
        cvColor15.setOnClickListener{handleCardSelection(cvColor15, allCards)}
        cvColor16.setOnClickListener{handleCardSelection(cvColor16, allCards)}
        cvColor17.setOnClickListener{handleCardSelection(cvColor17, allCards)}
        cvColor18.setOnClickListener{handleCardSelection(cvColor18, allCards)}
        cvColor19.setOnClickListener{handleCardSelection(cvColor19, allCards)}
        cvColor20.setOnClickListener{handleCardSelection(cvColor20, allCards)}


        btnAddText.setOnClickListener {
            val newText = TextView(requireContext())
            newText.text = etText.text.toString()


            val frameLayoutParent = FrameLayout(requireContext())

            val layoutParamsParent = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )

            frameLayoutParent.layoutParams = layoutParamsParent



            val frameLayout = FrameLayout(requireContext())

            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            frameLayout.layoutParams = layoutParams


            newText.background = ContextCompat.getDrawable(requireContext(),R.drawable.textview_border_edit)

            newText.setPadding(26, 26, 26, 26)

            val textLayoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )

            frameLayout.addView(newText, textLayoutParams)


            val imagePrueba = ImageView(requireContext())
            imagePrueba.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.baseline_next_plan_24))

            val imageLayoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )


            imageLayoutParams.gravity = Gravity.BOTTOM or Gravity.END
            imageLayoutParams.topMargin = 55

            frameLayout.addView(imagePrueba, imageLayoutParams)


            etText.text.clear()

            setOnTouchListener(newText)

            frameLayoutParent.addView(frameLayout)
            cvViewPreview.addView(frameLayoutParent)
        }




        dialogTitle.show()

    }



    @SuppressLint("ClickableViewAccessibility")

    private fun setOnTouchListener(newText: TextView, frame:FrameLayout) {
        var resizing = false
        var initialY = 0f
        val initialSize = 20f
        var deltaX = 0f
        var deltaY = 0f

        newText.setOnClickListener { newText.setBackgroundResource(R.drawable.textview_border_edit) }

        newText.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    newText.setBackgroundResource(R.drawable.textview_border_edit)
                    val isInsideResizeRegion = isInsideResizeRegion(event.x, event.y, newText)


                    initialY = event.rawY
                    deltaX = v.x - event.rawX
                    deltaY = v.y - event.rawY

                    if (isInsideResizeRegion) {
                        Log.i("TouchEvent", "Estás presionando en la esquina inferior derecha")
                        resizing = true
                        Log.i("TOuchEvent", "$resizing")
                    }else{
                        resizing = false
                    }


                }
                MotionEvent.ACTION_MOVE -> {
                    newText.setBackgroundResource(R.drawable.textview_border_edit)
                    Log.i("TOuchEvent", "move $resizing")
                    if (resizing) {
                        val deltaY = event.rawY - initialY
                        val newSize = initialSize + deltaY
                        newText.textSize = newSize.coerceIn(20f, 50f)
                    }else{
                        v.animate()
                            .x(event.rawX + deltaX)
                            .y(event.rawY + deltaY)
                            .setDuration(0)
                            .start()
                    }
                }

                MotionEvent.ACTION_POINTER_UP ->{
                    newText.setBackgroundResource(R.drawable.textview_border_not)

                }

            }
            true
        }


    }

    private fun isInsideResizeRegion(x: Float, y: Float, view: View): Boolean {
        val regionRight = view.right
        val regionBottom = view.bottom
        val touchSlop = 50


        return x >= regionRight - touchSlop && x <= regionRight && y >= regionBottom - touchSlop && y <= regionBottom
    }




    private fun handleCardSelection(cardSelected: MaterialCardView?, allCards: List<MaterialCardView>) {

        allCards.forEach{card ->
        card.strokeWidth = if(card == cardSelected ){
            resources.getDimensionPixelSize(R.dimen.stroke_cardselected)
        }else{
            3
        }
    }

        allCards.forEach { card ->
            card.strokeColor = if(card == cardSelected){
                ContextCompat.getColor(requireContext(), R.color.fourthSecond)
            }else{
                ContextCompat.getColor(requireContext(),R.color.secondarySecond)
            }
        }


    }





    private fun initDialogs() {
        dialogTitle = Dialog(requireContext())
        dialogTitle.setContentView(R.layout.dialog_palette_colors)
        dialogTitle.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))




    }

}