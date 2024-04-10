package com.sign.led.ui.createSigns

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
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
import android.view.animation.ScaleAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.card.MaterialCardView
import com.sign.led.R
import com.sign.led.databinding.FragmentCreateSignsBinding
import com.sign.led.domain.model.ItemViewFullModel
import com.sign.led.domain.model.SpinnerFontModel
import com.sign.led.domain.model.TextModel
import com.sign.led.ui.Singleton.ListItemsFullViewSingleton
import com.sign.led.ui.createSigns.adapter.SpinnerFontAdapter
import com.sign.led.ui.mysigns.MySignsViewModel
import com.sign.led.ui.utils.CustomToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateSignsFragment : Fragment() {


    //BINDING
    private var _binding: FragmentCreateSignsBinding? = null
    private val binding get() = _binding!!

    //ViewModel
    private val mySignsViewModel: MySignsViewModel by activityViewModels()

    //DIALOGS
    private lateinit var dialogTitle: Dialog
    private lateinit var dialogBackground: Dialog

    private lateinit var dialogSave: Dialog

    //LIST
    private val listTextNew = mutableListOf<TextView>()
    private val listViewNew = ArrayList<View>()

    //COMPONENTS
    private lateinit var fontFinal: String
    private var colorFinalTitle: Int? = null
    private var colorFinalBackground: Int? = null
    private lateinit var backgroundFinal: ImageView
    private lateinit var cvViewPreview: MaterialCardView
    private var backgroundState: Boolean = false
    private var animationSelected: Int = R.anim.anim_none
    private val listTextFinal = mutableListOf<TextModel>()
    private var animationState = false
    private var touchState = true
    private var speedSelection: Long = 0
    private var positionX: Float = 0.0f
    private var positionY: Float = 0.0f
    private var speedNormal: Long = 0
    private var speedSlow: Long = 0
    private var speedFast: Long = 0
    private var speedSelectionItem: Int = 0
    private var adCount = 0
    private var interstitial:InterstitialAd? = null


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
        initAds()
        initListeners()
    }



    private fun initListeners() {
        initDialogs()
        backgroundPixelInitial()

        interstitial?.fullScreenContentCallback = object : FullScreenContentCallback(){
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            }

            override fun onAdShowedFullScreenContent() {
                interstitial = null
            }

        }


        binding.btnStyleTitle.setOnClickListener {
            if(!animationState){
                showDialogTitle()
            }else{
                val customToast = CustomToast
                customToast.showCustomToast(requireContext(),getString(R.string.stop_animations))
            }
        }

        binding.btnStyleBackground.setOnClickListener {
            if(!animationState){
                showDialogBackground()
            }else{
                val customToast = CustomToast
                customToast.showCustomToast(requireContext(),getString(R.string.stop_animations))
            }
        }

        animationText()

        binding.btnUndo.setOnClickListener {
            val scaleAnimation = ScaleAnimation(
                1f, 1.2f,
                1f, 1.2f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
            ).apply {
                duration = 100
                interpolator = AccelerateDecelerateInterpolator()
            }

            binding.btnUndo.startAnimation(scaleAnimation)
            undoText()
        }

        binding.btnSave.setOnClickListener {

            binding.btnSave.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_save_pressed)
            binding.btnSave.alpha = 0f
            binding.btnSave.animate()
                .alpha(1f)
                .setDuration(120)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction {
                    binding.btnSave.postDelayed({
                        showDialogSaveSign()
                    }, 550)
                }
                .start()


        }




    }


    private fun initAds() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireContext(), getString(R.string.ADMOB_ID_ADS), adRequest, object : InterstitialAdLoadCallback(){
            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                interstitial = interstitialAd
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                interstitial = null
            }

        })


    }

    private fun checkCount(){
        if(adCount == 4){
            showAds()
            adCount = 0
            initAds()
        }
    }

    private fun showAds(){
        interstitial?.show(requireActivity())
    }


    private fun showDialogSaveSign() {
        val nameSignSave = dialogSave.findViewById<EditText>(R.id.etNameSign)
        val btnExitDialogSave = dialogSave.findViewById<ImageButton>(R.id.btnBackSave)
        val btnCheckDialogSave = dialogSave.findViewById<ImageButton>(R.id.btnCheckSave)


        btnExitDialogSave.setOnClickListener {
            dialogSave.dismiss()
            binding.btnSave.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_save)
            binding.btnSave.alpha = 0f
            binding.btnSave.animate()
                .alpha(1f)
                .setDuration(120)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }

        val onDismissDialogSave = DialogInterface.OnDismissListener {
            binding.btnSave.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_save)
            binding.btnSave.alpha = 0f
            binding.btnSave.animate()
                .alpha(1f)
                .setDuration(120)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .start()
        }

        btnCheckDialogSave.setOnClickListener {

            if (nameSignSave.text.toString()
                    .isNotEmpty() && nameSignSave.text.toString().length <= 28 && listTextFinal.size >= 1
            ) {


                Log.i("listFinalBd", "$listTextFinal")
                val nameSignFinal = nameSignSave.text.toString()
                val itemsFull = ItemViewFullModel(
                    -1,
                    nameSignFinal,
                    colorFinalBackground,
                    backgroundState,
                    listTextFinal.toList()
                )
                Log.i("listFinalBd", "$listTextFinal")
                mySignsViewModel.createSign(itemsFull)


                cvViewPreview.removeAllViews()
                cvViewPreview.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                backgroundState = false
                colorFinalBackground = null
                listViewNew.clear()
                listTextNew.clear()
                listTextFinal.clear()
                dialogSave.dismiss()
                nameSignSave.setText("")
                Log.i("listFinalBd", "clear $listTextFinal")


            } else if (nameSignSave.text.toString().isEmpty()) {
                val customToast = CustomToast
                customToast.showCustomToast(requireContext(),getString(R.string.name_valid))

            } else if (listTextFinal.size < 1) {
                val customToast = CustomToast
                customToast.showCustomToast(requireContext(),getString(R.string.save_text_one))

            }


        }




        dialogSave.setOnDismissListener(onDismissDialogSave)

        dialogSave.show()

    }


    private fun undoText() {

        if (listTextNew.isNotEmpty() && listViewNew.isNotEmpty() && listTextFinal.isNotEmpty()) {
            val lastText = listTextNew.last()
            val lastView = listViewNew.last()

            cvViewPreview.forEach { viewCard ->
                if (viewCard == lastText) {
                    cvViewPreview.removeView(viewCard)

                }
            }

            cvViewPreview.forEach { viewCard ->
                if (viewCard == lastView) {
                    cvViewPreview.removeView(viewCard)
                }
            }

            listViewNew.removeAt(listViewNew.lastIndex)
            listTextNew.removeAt(listTextNew.lastIndex)
            listTextFinal.removeAt(listTextFinal.lastIndex)

        }

    }

    private fun animationText() {

        val animatorSet = AnimatorSet()


        binding.btnPlayAnimation.setOnClickListener {

            val animationPlay =
                ObjectAnimator.ofFloat(binding.ivPlayAnimation, "alpha", 0f, 1f).apply {
                    duration = 200
                    interpolator = AccelerateDecelerateInterpolator()
                }
            animationPlay.start()



            if (animationState) {

                binding.ivPlayAnimation.setImageResource(R.drawable.ic_play_animation)

                animatorSet.end()

                listTextNew.forEach { text ->
                    text.ellipsize = TextUtils.TruncateAt.END
                    text.layoutParams.width = -2
                    text.isSelected = false




                    text.clearAnimation()
                }


            } else {
                binding.ivPlayAnimation.setImageResource(R.drawable.ic_pause_animation)

                listViewNew.forEach { viewNewList ->
                    viewNewList.setBackgroundResource(0)
                }

                listTextNew.forEachIndexed { index, text ->
                    val textModel = listTextFinal[index]
                    text.ellipsize = null
                    val textWidth = text.paint.measureText(text.text.toString())





                    val animationResourceTypeName =
                        requireContext().resources.getResourceTypeName(textModel.animation)


                    //Type Animator


                    if (animationResourceTypeName == "animator") {

                            val animationFinalPreviewAnimator  =
                                AnimatorInflater.loadAnimator(requireContext(), textModel.animation)
                            animationFinalPreviewAnimator.duration = textModel.speedAnimation
                            animationFinalPreviewAnimator.setTarget(text)
                            animatorSet.play(animationFinalPreviewAnimator)



                    } else if (animationResourceTypeName == "anim") {
                        if(textModel.animation == R.anim.anim_horizontal_displacement && textWidth > cvViewPreview.width.toFloat()){

                                text.layoutParams.width = textWidth.toInt()
                                text.ellipsize = TextUtils.TruncateAt.MARQUEE
                                text.isSelected = true


                        }else{
                            val animation =
                                AnimationUtils.loadAnimation(requireContext(), textModel.animation)
                            animation.duration = textModel.speedAnimation
                            text.animation = animation
                            text.startAnimation(animation)
                        }
                    }



                }

                animatorSet.start()

            }




            animationState = !animationState
            touchState = !touchState

        }

        binding.btnFullView.setOnClickListener {

            val items =
                ItemViewFullModel(null, null, colorFinalBackground, backgroundState, listTextFinal)
            ListItemsFullViewSingleton.setListItems(items)

            val customToast = CustomToast
            customToast.showCustomToast(requireContext(),getString(R.string.loading_screen))

            Log.i("aveeerrrs", "$adCount")


            findNavController().navigate(
                CreateSignsFragmentDirections.actionCreateSignsFragmentToSignFullViewActivity2(
                    -1,
                    "signTemporal"
                )
            )


            adCount += 1
            checkCount()


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

        cardTitleStyleColorPalette(
            "background",
            cvColor1,
            cvColor2,
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
            cvColor20
        )


        val spTextureItems = resources.getStringArray(R.array.spTextureItems)
        val spinnerTextureAdapter =
            ArrayAdapter(requireContext(), R.layout.spinner_selected, spTextureItems)

        spinnerTextureAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerTexture.adapter = spinnerTextureAdapter

        spinnerTexture.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                when (spinnerTexture.selectedItemPosition) {
                    0 -> backgroundPixelVisibleFalse()
                    1 -> backgroundPixelVisibleTrue()
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }




        btnAddBackground.setOnClickListener {
            cvViewPreview.setCardBackgroundColor(colorFinalBackground!!)


            binding.tvStyleBackground.setText(R.string.edit_style)
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

    private fun backgroundPixelInitial() {
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
    @SuppressLint("ClickableViewAccessibility")
    private fun showDialogTitle() {
        val btnAddText = dialogTitle.findViewById<ImageButton>(R.id.btnCheck)
        val btnExitDialog = dialogTitle.findViewById<ImageButton>(R.id.btnBack)
        val etText = binding.etText


        val spinnerFont = dialogTitle.findViewById<Spinner>(R.id.spFont)
        val spinnerAnimation = dialogTitle.findViewById<Spinner>(R.id.spAnimation)
        val spinnerSpeedAnimation = dialogTitle.findViewById<Spinner>(R.id.spSpeedAnimation)

        spinnerSpeedAnimation.isEnabled = false

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
                2 -> customTypeface3
                3 -> customTypeface4
                4 -> customTypeface5
                5 -> customTypeface6
                6 -> customTypeface7
                7 -> customTypeface8
                else -> customTypeface1
            }

            options.add(SpinnerFontModel(text, font))
        }


        val spinnerFontAdapter = SpinnerFontAdapter(requireContext(), options)
        spinnerFont.adapter = spinnerFontAdapter


        spinnerFont.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                fontFinal = when (spinnerFont.selectedItemPosition) {
                    0 -> "dmsan.ttf"
                    1 -> "leaguegothic.ttf"
                    2 -> "dmseriftext.ttf"
                    3 -> "fugazone.ttf"
                    4 -> "sixtyfour.ttf"
                    5 -> "pressstart.ttf"
                    6 -> "bungeeshade.ttf"
                    7 -> "amatic.ttf"
                    else -> {
                        "dmsan.ttf"
                    }
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {


            }

        }


        //SPINNER ANIMATION
        val spAnimationItems = resources.getStringArray(R.array.spAnimationItems)

        val spinnerAnimationAdapter =
            ArrayAdapter(requireContext(), R.layout.spinner_selected, spAnimationItems)
        spinnerAnimationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerAnimation.adapter = spinnerAnimationAdapter

        spinnerAnimation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                speedNormal = speedAnimationNormal(position)
                speedSlow = speedAnimationSlow(position)
                speedFast = speedAnimationFast(position)

                animationSelected = when (spinnerAnimation.selectedItemPosition) {
                    0 -> R.anim.anim_none
                    1 -> R.anim.anim_horizontal_displacement
                    2 -> R.anim.anim_blink
                    3 -> R.anim.anim_float_text
                    4 -> R.animator.anim_rotate
                    5 -> R.animator.anim_text_zoom
                    else -> R.anim.anim_none
                }

                spinnerSpeedAnimation.isEnabled = position != 0

            }


            override fun onNothingSelected(parent: AdapterView<*>?) {
            }


        }


        //SPINNER SPEED ANIMATION
        val spSpeedAnimationItems = resources.getStringArray(R.array.spSpeedAnimationItems)

        val spinnerSpeedAnimationAdapter =
            ArrayAdapter(requireContext(), R.layout.spinner_selected, spSpeedAnimationItems)
        spinnerSpeedAnimationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_items)

        spinnerSpeedAnimation.adapter = spinnerSpeedAnimationAdapter




        spinnerSpeedAnimation.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                speedSelectionItem = position


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

        cardTitleStyleColorPalette(
            "text",
            cvColor1,
            cvColor2,
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
            cvColor20
        )







        btnAddText.setOnClickListener {

            val etTextFinal = etText.text.toString()

            if (etTextFinal.isNotEmpty()) {
                val newText = TextView(requireContext())
                newText.text = etTextFinal
                val textNew = newText.text.toString()
                newText.textSize = 20f
                newText.maxLines = 1
                newText.isSingleLine = true
                newText.ellipsize = TextUtils.TruncateAt.END
                newText.setTextColor(colorFinalTitle!!)
                val fontSelected = Typeface.createFromAsset(requireContext().assets, fontFinal)

                newText.typeface = fontSelected


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

                newText.setPadding(20, 20, 20, 20)


                val newView = View(requireContext())

                val viewLayoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT

                )

                viewLayoutParams.width = textWidth + 40
                viewLayoutParams.height = textHeight + 40
                newView.layoutParams = viewLayoutParams



                when (speedSelectionItem) {
                    0 -> speedSelection = speedNormal
                    1 -> speedSelection = speedSlow
                    2 -> speedSelection = speedFast
                }
                Log.i("position", "$speedSelection")
                val animationFinal = animationSelected
                val animationSpeed = speedSelection

                newText.id = View.generateViewId()

                positionX = 0.0f
                positionY = 0.0f

                val textFinalNew = TextModel(
                    newText.id,
                    newText.text.toString(),
                    newText.textSize,
                    fontFinal,
                    colorFinalTitle!!,
                    animationFinal,
                    animationSpeed,
                    positionX,
                    positionY
                )
                listTextFinal.add(textFinalNew)



                listTextNew.add(newText)
                listViewNew.add(newView)
                setOnTouchListener(newText, newView)


                cvViewPreview.addView(newText, 0)
                cvViewPreview.removeView(backgroundFinal)
                cvViewPreview.addView(backgroundFinal)
                cvViewPreview.addView(newView)


                etText.text.clear()
                dialogTitle.dismiss()




                if (listTextNew.size > 0) {
                    etText.setHint(R.string.another_text)
                }

                etText.clearFocus()

            } else {

                val customToast = CustomToast
                customToast.showCustomToast(requireContext(),getString(R.string.textNotValid))
            }


        }





        btnExitDialog.setOnClickListener { dialogTitle.dismiss() }
        dialogTitle.show()

    }

    private fun speedAnimationNormal(position: Int): Long {

        Log.i("item position", "$position")
        return when (position) {
            1 -> 6000 //horizontal displacement
            2 -> 1000 //blink
            3 -> 3000 //float text
            4 -> 8000 //rotate
            5 -> 4200 //text zoom
            else -> 0 //none
        }

    }

    private fun speedAnimationSlow(position: Int): Long {

        return when (position) {
            1 -> 10000 //horizontal displacement
            2 -> 4000 //blink
            3 -> 5000 //float text
            4 -> 12000 //rotate
            5 -> 7200 //text zoom
            else -> 0 //none
        }

    }

    private fun speedAnimationFast(position: Int): Long {

        return when (position) {
            1 -> 4000 //horizontal displacement
            2 -> 400 //blink
            3 -> 900 //float text
            4 -> 3000 //rotate
            5 -> 2000 //text zoom
            else -> 0 //none

        }

    }


    @SuppressLint("ClickableViewAccessibility")
    private fun setOnTouchListener(newText: TextView, newView: View) {

        var resizing = false
        var initialY = 0f
        val initialSize = 20f
        var deltaX = 0f
        var deltaY = 0f


        val indicesNewTextID = newText.id

        val scrollView = binding.svFragmentCreateSign


        newView.setOnTouchListener { v, event ->



            if (!touchState) {

                return@setOnTouchListener false
            }


            when (event.action) {

                MotionEvent.ACTION_DOWN -> {

                    scrollView.requestDisallowInterceptTouchEvent(true)

                    //Collocation Background to vista
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


                    val textHeight = newText.measuredHeight
                    val textWidth = newText.measuredWidth
                    newView.layoutParams.height = textHeight
                    newView.layoutParams.width = textWidth
                    newView.requestLayout()


                    cvViewPreview.setOnClickListener {
                        listViewNew.forEach { viewNewList ->
                            viewNewList.setBackgroundResource(0)
                        }


                    }

                    val isInsideResizeRegion = isInsideResizeRegion(event.x, event.y, newView)
                    val isInsideLeftBottomRegion =
                        isInsideLeftBottomRegion(event.x, event.y, newView)

                    initialY = event.rawY
                    deltaX = v.x - event.rawX
                    deltaY = v.y - event.rawY


                    if (isInsideResizeRegion) {
                        Log.i("TouchEvent", "Estás presionando en la esquina inferior derecha")
                        resizing = true
                        Log.i("TOuchEvent", "$resizing")
                    } else if (isInsideLeftBottomRegion) {

                        cvViewPreview.removeView(newText)
                        cvViewPreview.removeView(newView)
                        listViewNew.remove(newView)
                        listTextNew.remove(newText)

                        var textModelSearch: Int? = null
                        for ((index, text) in listTextFinal.withIndex()) {
                            if (text.id == indicesNewTextID) {
                                textModelSearch = index
                                break
                            }
                        }
                        if (textModelSearch != null) {
                            listTextFinal.removeAt(textModelSearch)
                        }


                    } else {
                        resizing = false
                    }


                }

                MotionEvent.ACTION_MOVE -> {
                    scrollView.requestDisallowInterceptTouchEvent(true)

                    Log.i("TOuchEvent", "move $resizing")
                    if (resizing) {
                        val deltaY = event.rawY - initialY
                        val newSize = initialSize + deltaY
                        newText.textSize = newSize.coerceIn(20f, 50f)
                        newText.measure(0, 0)
                        val textHeight = newText.measuredHeight
                        val textWidth = newText.measuredWidth
                        newView.layoutParams.height = textHeight
                        newView.layoutParams.width = textWidth
                        newView.requestLayout()
                    } else {

                        val newX = event.rawX + deltaX
                        val newY = event.rawY + deltaY



                        positionX = newX / cvViewPreview.width
                        positionY = newY / cvViewPreview.height

                        var textModelSearchBd: Int? = null

                        Log.i("textModelSearch", "$textModelSearchBd")
                        for ((index, text) in listTextFinal.withIndex()) {
                            if (text.id == indicesNewTextID) {
                                textModelSearchBd = index
                                break
                            }
                        }

                        Log.i("textModelSearch", "$textModelSearchBd")

                        if(textModelSearchBd != null){
                            val textModelSelected = listTextFinal[textModelSearchBd]
                            textModelSelected.positionX = positionX
                            textModelSelected.positionY = positionY
                            textModelSelected.size = newText.textSize
                        }



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

                MotionEvent.ACTION_UP ->{
                    scrollView.requestDisallowInterceptTouchEvent(false)
                }


            }


            true

        }







    }


    private fun cardTitleStyleColorPalette(
        id: String,
        cvColor1: MaterialCardView, cvColor2: MaterialCardView, cvColor3: MaterialCardView,
        cvColor4: MaterialCardView, cvColor5: MaterialCardView, cvColor6: MaterialCardView,
        cvColor7: MaterialCardView, cvColor8: MaterialCardView, cvColor9: MaterialCardView,
        cvColor10: MaterialCardView, cvColor11: MaterialCardView, cvColor12: MaterialCardView,
        cvColor13: MaterialCardView, cvColor14: MaterialCardView, cvColor15: MaterialCardView,
        cvColor16: MaterialCardView, cvColor17: MaterialCardView, cvColor18: MaterialCardView,
        cvColor19: MaterialCardView, cvColor20: MaterialCardView
    ) {


        val allCards = listOf(
            cvColor1, cvColor2,
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
            cvColor20
        )

        //DEFAULT
        handleCardSelection(id, cvColor1, allCards)

        cvColor1.setOnClickListener { handleCardSelection(id, cvColor1, allCards) }
        cvColor2.setOnClickListener { handleCardSelection(id, cvColor2, allCards) }
        cvColor3.setOnClickListener { handleCardSelection(id, cvColor3, allCards) }
        cvColor4.setOnClickListener { handleCardSelection(id, cvColor4, allCards) }
        cvColor5.setOnClickListener { handleCardSelection(id, cvColor5, allCards) }
        cvColor6.setOnClickListener { handleCardSelection(id, cvColor6, allCards) }
        cvColor7.setOnClickListener { handleCardSelection(id, cvColor7, allCards) }
        cvColor8.setOnClickListener { handleCardSelection(id, cvColor8, allCards) }
        cvColor9.setOnClickListener { handleCardSelection(id, cvColor9, allCards) }
        cvColor10.setOnClickListener { handleCardSelection(id, cvColor10, allCards) }
        cvColor11.setOnClickListener { handleCardSelection(id, cvColor11, allCards) }
        cvColor12.setOnClickListener { handleCardSelection(id, cvColor12, allCards) }
        cvColor13.setOnClickListener { handleCardSelection(id, cvColor13, allCards) }
        cvColor14.setOnClickListener { handleCardSelection(id, cvColor14, allCards) }
        cvColor15.setOnClickListener { handleCardSelection(id, cvColor15, allCards) }
        cvColor16.setOnClickListener { handleCardSelection(id, cvColor16, allCards) }
        cvColor17.setOnClickListener { handleCardSelection(id, cvColor17, allCards) }
        cvColor18.setOnClickListener { handleCardSelection(id, cvColor18, allCards) }
        cvColor19.setOnClickListener { handleCardSelection(id, cvColor19, allCards) }
        cvColor20.setOnClickListener { handleCardSelection(id, cvColor20, allCards) }
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


    private fun handleCardSelection(
        id: String,
        cardSelected: MaterialCardView?,
        allCards: List<MaterialCardView>
    ) {

        when (id) {
            "background" -> colorFinalBackground = cardSelected?.cardBackgroundColor?.defaultColor!!
            "text" -> colorFinalTitle = cardSelected?.cardBackgroundColor?.defaultColor!!
        }



        cardSelected?.cardBackgroundColor
        allCards.forEach { card ->
            card.strokeWidth = if (card == cardSelected) {
                resources.getDimensionPixelSize(R.dimen.stroke_cardselected)
            } else {
                3
            }
        }

        allCards.forEach { card ->
            card.strokeColor = if (card == cardSelected) {
                ContextCompat.getColor(requireContext(), R.color.fourthSecond)
            } else {
                ContextCompat.getColor(requireContext(), R.color.secondarySecond)
            }
        }


    }


    private fun initDialogs() {
        dialogTitle = Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_palette_colors)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialogBackground = Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_background)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        dialogSave = Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_save)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        listTextNew.clear()
        listViewNew.clear()
        colorFinalBackground = null
        colorFinalTitle = null
        backgroundState = false
        fontFinal = ""
        listTextFinal.clear()
        animationSelected = R.anim.anim_none
        animationState = false
        touchState = true
        cvViewPreview.removeAllViews()
        speedSelection = 0
        positionX = 0.0f
        positionY = 0.0f
        speedNormal = 0
        speedSlow = 0
        speedFast = 0
        speedSelectionItem = 0

        if(::backgroundFinal.isInitialized){
            backgroundFinal.setImageResource(0)
        }



        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        listTextNew.clear()
        listViewNew.clear()
        colorFinalBackground = null
        colorFinalTitle = null
        backgroundState = false
        fontFinal = ""
        listTextFinal.clear()
        animationSelected = R.anim.anim_none
        touchState = true
        animationState = false
        cvViewPreview.removeAllViews()
        speedSelection = 0
        positionX = 0.0f
        positionY = 0.0f
        speedNormal = 0
        speedSlow = 0
        speedFast = 0
        speedSelectionItem = 0


        if(::backgroundFinal.isInitialized){
            backgroundFinal.setImageResource(0)
        }

        _binding = null
    }

}