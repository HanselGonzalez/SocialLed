package com.sign.led.ui.createSigns

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextPaint
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.sign.led.R
import com.sign.led.databinding.FragmentCreateSignsBinding
import com.sign.led.domain.model.SpinnerFontModel
import com.sign.led.domain.model.TextModel
import com.sign.led.ui.createSigns.adapter.SpinnerFontAdapter


class CreateSignsFragment : Fragment() {


    private var _binding:FragmentCreateSignsBinding? = null
    private val binding get() = _binding!!
    private lateinit var dialogTitle: Dialog
    private lateinit var dialogBackground: Dialog
    private val listTextNew = ArrayList<TextView>()
    private val listViewNew = ArrayList<View>()

    private lateinit var fontFinal:String

    private var colorFinalTitle:Int? = null
    private var colorFinalBackground:Int? = null
    private lateinit var backgroundFinal:ImageView
    private lateinit var cvViewPreview : MaterialCardView
    private var backgroundState:Boolean = false
    private var animationSelected:Int = R.anim.anim_none
    private val listTextFinal = mutableListOf<TextModel>()
    private var animationState = false
    private var touchState = true
    private var speedSelection:Long = 3000


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateSignsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cvViewPreview = binding.cvViewPreview
        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        initDialogs()
        backgroundPixelInitial()




        binding.btnStyleTitle.setOnClickListener {
            showDialogTitle()
        }

        binding.btnStyleBackground.setOnClickListener{
            showDialogBackground()
        }

        animationText()



    }

    private fun animationText() {


        binding.btnPlayAnimation.setOnClickListener{


            val animationPlay = ObjectAnimator.ofFloat(binding.ivPlayAnimation, "alpha", 0f, 1f).apply {
                duration = 500
                interpolator = AccelerateDecelerateInterpolator()
            }
            animationPlay.start()


            if(animationState){

                binding.ivPlayAnimation.setImageResource(R.drawable.ic_play_animation)

                listTextNew.forEach{text ->
                    text.clearAnimation()
                }

            }else{
                binding.ivPlayAnimation.setImageResource(R.drawable.ic_pause_animation)

                listViewNew.forEach { viewNewList ->
                    viewNewList.setBackgroundResource(0)
                }

                listTextNew.forEachIndexed { index, text ->


                    val textModel = listTextFinal[index]
                    val animationFinalPreview = AnimationUtils.loadAnimation(requireContext(),textModel.animation)
                    animationFinalPreview.duration = textModel.speedAnimation

                    text.animation = animationFinalPreview
                    text.startAnimation(text.animation)


                }
            }

            animationState = !animationState
            touchState = !touchState

        }

        binding.btnFullView.setOnClickListener{
            findNavController().navigate(
                R.id.signFullViewActivity
            )
        }

    }


    //BACKGROUND
    private fun showDialogBackground() {
        val btnAddBackground = dialogBackground.findViewById<ImageButton>(R.id.btnCheck)
        val btnExitDialogBackground = dialogBackground.findViewById<ImageButton>(R.id.btnBack)
        val spinnerTexture = dialogBackground.findViewById<Spinner>(R.id.spTexture)



        val cvColor1 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor1)
        val cvColor2 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor2)
        val cvColor3 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor3)
        val cvColor4 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor4)
        val cvColor5 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor5)
        val cvColor6 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor6)
        val cvColor7 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor7)
        val cvColor8 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor8)
        val cvColor9 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor9)
        val cvColor10 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor10)
        val cvColor11 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor11)
        val cvColor12 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor12)
        val cvColor13 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor13)
        val cvColor14 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor14)
        val cvColor15 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor15)
        val cvColor16 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor16)
        val cvColor17 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor17)
        val cvColor18 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor18)
        val cvColor19 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor19)
        val cvColor20 = dialogBackground.findViewById<MaterialCardView>(R.id.cvColor20)

        cardTitleStyleColorPalette(cvColor1,cvColor2,cvColor3,cvColor4,cvColor5,cvColor6,cvColor7,cvColor8,
            cvColor9,cvColor10,cvColor11,cvColor12,cvColor13,cvColor14,cvColor15,cvColor16,cvColor17,cvColor18,cvColor19,cvColor20)




        val spTextureItems = resources.getStringArray(R.array.spTextureItems)
        val spinnerTextureAdapter = ArrayAdapter(requireContext(), R.layout.spinner_selected, spTextureItems)

        spinnerTextureAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerTexture.adapter = spinnerTextureAdapter

        spinnerTexture.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                when(spinnerTexture.selectedItemPosition){
                    0 -> backgroundPixelVisibleFalse()
                    1 -> backgroundPixelVisibleTrue()
                }



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }




        btnAddBackground.setOnClickListener{
            cvViewPreview.setCardBackgroundColor(colorFinalBackground!!)


            dialogBackground.dismiss()
        }

        btnExitDialogBackground.setOnClickListener { dialogBackground.dismiss() }

        dialogBackground.show()

    }

    private fun backgroundPixelVisibleTrue() {

        backgroundFinal.visibility = ImageView.VISIBLE
        backgroundState = true

    }

    private fun backgroundPixelVisibleFalse() {

        backgroundFinal.visibility = ImageView.INVISIBLE
        backgroundState = false

    }

    private fun backgroundPixelInitial(){
        backgroundFinal = ImageView(requireContext())
        backgroundFinal.setImageResource(R.drawable.background_pixel)
        backgroundFinal.visibility = ImageView.GONE
        backgroundFinal.scaleType = ImageView.ScaleType.FIT_XY

        val imageLayoutParams = ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT

        )


        backgroundFinal.layoutParams = imageLayoutParams

        cvViewPreview.addView(backgroundFinal)


    }






    //TEXT
    private fun showDialogTitle() {
        val btnAddText = dialogTitle.findViewById<ImageButton>(R.id.btnCheck)
        val btnExitDialog = dialogTitle.findViewById<ImageButton>(R.id.btnBack)
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



        val spinnerFontAdapter = SpinnerFontAdapter(requireContext(),options)
        spinnerFont.adapter = spinnerFontAdapter


        spinnerFont.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                val selectedItemFont = spinnerFontAdapter.getSelectedFontTypeface(position)
                 fontFinal = selectedItemFont!!



            }

            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

        }



        //SPINNER ANIMATION

        val spAnimationItems = resources.getStringArray(R.array.spAnimationItems)

        val spinnerAnimationAdapter = ArrayAdapter(requireContext(), R.layout.spinner_selected, spAnimationItems)
        spinnerAnimationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerAnimation.adapter = spinnerAnimationAdapter

        spinnerAnimation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                animationSelected = when(spinnerAnimation.selectedItemPosition){
                    0 -> R.anim.anim_none
                    1 -> R.anim.anim_horizontal_displacement
                    2 -> R.anim.anim_blink
                    3 -> R.anim.anim_float_text
                    4 -> R.anim.anim_rotate
                    5 -> R.anim.anim_text_zoom
                    else -> R.anim.anim_none
                }




            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
            }


        }



        //SPINER SPEED ANIMATION
        val spSpeedAnimationItems = resources.getStringArray(R.array.spSpeedAnimationItems)

        val spinnerSpeedAnimationAdapter = ArrayAdapter(requireContext(), R.layout.spinner_selected, spSpeedAnimationItems)
        spinnerSpeedAnimationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerSpeedAnimation.adapter = spinnerSpeedAnimationAdapter



        spinnerSpeedAnimation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                speedSelection = when (spinnerSpeedAnimation.selectedItemPosition) {
                    0 -> 3000
                    1 -> 5000
                    2 -> 9000
                    else -> {3000}
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }





























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

        cardTitleStyleColorPalette(cvColor1,cvColor2,cvColor3,cvColor4,cvColor5,cvColor6,cvColor7,cvColor8,
                cvColor9,cvColor10,cvColor11,cvColor12,cvColor13,cvColor14,cvColor15,cvColor16,cvColor17,cvColor18,cvColor19,cvColor20)







        btnAddText.setOnClickListener {

            val etTextFinal = etText.text.toString()

            if(etTextFinal.isNotEmpty()){
                val newText = TextView(requireContext())
                newText.text = etTextFinal
                val textNew = newText.text.toString()
                newText.textSize = 20f
                newText.maxLines = 1
                newText.ellipsize = TextUtils.TruncateAt.END
                newText.setTextColor(colorFinalTitle!!)
                newText.elevation = -10f
                newText.typeface = fontFinal





                val textLayoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT

                )


                newText.layoutParams = textLayoutParams



                val textBounds = Rect()
                val textPaint = TextPaint()
                textPaint.textSize = newText.textSize
                textPaint.typeface = newText.typeface
                textPaint.getTextBounds(textNew, 0, textNew.length, textBounds)

                val textWidth = textBounds.width()
                val textHeight = textBounds.height()


                newText.setPadding(20,10,20,20)



                val newView = View(requireContext())

                val viewLayoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT

                )

                viewLayoutParams.width = textWidth + 40
                viewLayoutParams.height = textHeight + 40
                newView.layoutParams = viewLayoutParams






                listTextNew.add(newText)
                listViewNew.add(newView)
                setOnTouchListener(newText,newView)




                val animationFinal = animationSelected
                val animationSpeed = speedSelection


                val textFinalNew = TextModel(newText.text.toString(), newText.textSize, fontFinal, colorFinalTitle!!, animationFinal, animationSpeed)
                listTextFinal.add(textFinalNew)



                cvViewPreview.addView(newText)
                cvViewPreview.removeView(backgroundFinal)
                cvViewPreview.addView(backgroundFinal,0)
                cvViewPreview.addView(newView)





                etText.text.clear()
                dialogTitle.dismiss()




                if (listTextNew.size>0){
                    etText.setHint(R.string.another_text)
                }

            }else{
                Toast.makeText(requireContext(),"Ingresa un Texto Valido", Toast.LENGTH_SHORT).show()
            }





        }





        btnExitDialog.setOnClickListener{dialogTitle.dismiss()}
        dialogTitle.show()

    }



    @SuppressLint("ClickableViewAccessibility")
    private fun setOnTouchListener(newText: TextView, newView: View) {
        var resizing = false
        var initialY = 0f
        val initialSize = 20f
        var deltaX = 0f
        var deltaY = 0f







        newView.setOnTouchListener { v, event ->

            if (!touchState) {

                return@setOnTouchListener false
            }

            when (event.action) {

                MotionEvent.ACTION_DOWN -> {

                    val viewIndex = listViewNew.indexOfFirst { it == newView }
                    if (viewIndex != -1) {

                        listViewNew.forEachIndexed { index, viewNewList ->
                            val backgroundResource = if (index == viewIndex) {
                                R.drawable.layer_drawable

                            } else {
                                0
                            }
                            viewNewList.setBackgroundResource(backgroundResource)
                        }

                    }


                    cvViewPreview.setOnClickListener {
                        listViewNew.forEach { viewNewList ->
                            viewNewList.setBackgroundResource(0)
                        }




                    }

                    val isInsideResizeRegion = isInsideResizeRegion(event.x, event.y, newView)
                    val isInsideLeftBottomRegion = isInsideLeftBottomRegion(event.x, event.y, newView)

                    initialY = event.rawY
                    deltaX = v.x - event.rawX
                    deltaY = v.y - event.rawY


                    if (isInsideResizeRegion ) {
                        Log.i("TouchEvent", "Estás presionando en la esquina inferior derecha")
                        resizing = true
                        Log.i("TOuchEvent", "$resizing")
                    } else if (isInsideLeftBottomRegion) {
                        cvViewPreview.removeView(newText)
                        cvViewPreview.removeView(newView)
                        listViewNew.remove(newView)
                        listTextNew.remove(newText)

                    } else {
                        resizing = false
                    }


                }

                MotionEvent.ACTION_MOVE -> {
                    Log.i("TOuchEvent", "move $resizing")
                    if (resizing) {
                        val deltaY = event.rawY - initialY
                        val newSize = initialSize + deltaY
                        newText.textSize = newSize.coerceIn(20f, 50f)
                        newText.measure(10, 0)
                        val textHeight = newText.measuredHeight
                        val textWidth = newText.measuredWidth
                        newView.layoutParams.height = textHeight
                        newView.layoutParams.width = textWidth
                        newView.requestLayout()
                    } else {
                        val newX = event.rawX + deltaX
                        val newY = event.rawY + deltaY
                        newView.animate()
                            .x(newX)
                            .y(newY)
                            .setDuration(0)
                            .start()

                        newText.animate()
                            .x(newX)
                            .y(newY)
                            .setDuration(0)
                            .start()
                    }

                }


            }
            true

        }


    }


    private fun cardTitleStyleColorPalette(cvColor1:MaterialCardView,cvColor2:MaterialCardView,cvColor3:MaterialCardView,
                                           cvColor4:MaterialCardView,cvColor5:MaterialCardView,cvColor6:MaterialCardView,
                                           cvColor7:MaterialCardView,cvColor8:MaterialCardView,cvColor9:MaterialCardView,
                                           cvColor10:MaterialCardView,cvColor11:MaterialCardView,cvColor12:MaterialCardView,
                                           cvColor13:MaterialCardView,cvColor14:MaterialCardView,cvColor15:MaterialCardView,
                                           cvColor16:MaterialCardView,cvColor17:MaterialCardView,cvColor18:MaterialCardView,
                                           cvColor19:MaterialCardView, cvColor20:MaterialCardView){


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
    }





    private fun isInsideResizeRegion(x: Float, y: Float, view: View): Boolean {
        val regionRight = view.right
        val regionBottom = view.bottom
        val touchSlop = 50


        return x >= regionRight - touchSlop && x <= regionRight && y >= regionBottom - touchSlop && y <= regionBottom
    }


    private fun isInsideLeftBottomRegion(x: Float, y: Float, view: View): Boolean {
        val regionLeft = view.left
        val regionBottom = view.bottom
        val touchSlop = 50

        return x >= regionLeft && x <= regionLeft + touchSlop && y >= regionBottom - touchSlop && y <= regionBottom
    }


    private fun handleCardSelection(cardSelected: MaterialCardView?, allCards: List<MaterialCardView>) {

        colorFinalTitle = cardSelected?.cardBackgroundColor?.defaultColor!!
        colorFinalBackground = cardSelected?.cardBackgroundColor?.defaultColor!!

        cardSelected?.cardBackgroundColor
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

        dialogBackground = Dialog(requireContext())
        dialogBackground.setContentView(R.layout.dialog_background)
        dialogBackground.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))




    }

}