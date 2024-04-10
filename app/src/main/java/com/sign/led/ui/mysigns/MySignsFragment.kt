package com.sign.led.ui.mysigns

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.sign.led.R
import com.sign.led.databinding.FragmentMySignsBinding
import com.sign.led.domain.model.ItemViewFullModel
import com.sign.led.ui.mysigns.Adapter.MySignsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MySignsFragment : Fragment() {


    private var _binding : FragmentMySignsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:MySignsAdapter
    private val mySignsViewModel:MySignsViewModel by activityViewModels()
    private lateinit var dialogDeleteSign: Dialog
    private var adCount = 0
    private var interstitial:InterstitialAd? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initDialog()
        mySignsViewModel.getSigns()
        initAds()
        initListeners()
        initList()
        initUIState()

    }

    private fun initListeners() {
        interstitial?.fullScreenContentCallback = object : FullScreenContentCallback(){
            override fun onAdDismissedFullScreenContent() {
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            }

            override fun onAdShowedFullScreenContent() {
                interstitial = null
            }

        }
    }

    private fun initDialog() {
        dialogDeleteSign = Dialog(requireContext()).apply {
            setContentView(R.layout.dialog_delete_sign)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mySignsViewModel.state.collect(){
                    when(it){
                        is MySignsState.Error -> errorState()
                        MySignsState.Initial -> initialState()
                        MySignsState.Loading -> loadingState()
                        is MySignsState.Success -> successState(it)
                    }
                }
            }
        }
    }


    private fun successState(state: MySignsState.Success) {
        binding.pbMySigns.isVisible = false
        adapter.updateDate(state.itemsFull)

        if(state.itemsFull.isNotEmpty()){
            binding.tvEmptyListMySign.isVisible = false
        }



    }

    private fun loadingState() {
        binding.pbMySigns.isVisible = true
        
    }

    private fun initialState() {
        binding.pbMySigns.isVisible = false
        binding.tvEmptyListMySign.isVisible = true


    }

    private fun errorState() {
        binding.pbMySigns.isVisible = false

    }

    private fun initList() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = MySignsAdapter(
            navigateToFullView = { navigateToFullView(it)},
            onDeleteClick = { onDeleteClickSign(it)}
        )

        binding.apply {
            rvMySigns.layoutManager = LinearLayoutManager(rvMySigns.context)
            rvMySigns.adapter = adapter
        }

    }


    private fun onDeleteClickSign(itemsFull:ItemViewFullModel){

        dialogDeleteSign.findViewById<ImageButton>(R.id.btnBackDelete).setOnClickListener {
            dialogDeleteSign.dismiss()
        }

        dialogDeleteSign.findViewById<ImageButton>(R.id.btnCheckDelete).setOnClickListener {
            mySignsViewModel.deleteSign(itemsFull)
            dialogDeleteSign.dismiss()
        }


        dialogDeleteSign.show()
    }

    private fun navigateToFullView(idItem:Long){

        findNavController().navigate(MySignsFragmentDirections.actionMySignsFragment2ToSignFullViewActivity(idItem,"signBd"))
        adCount += 1
        checkCount()
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
        if(adCount == 2){
            showAds()
            adCount = 0
            initAds()
        }
    }

    private fun showAds(){
        interstitial?.show(requireActivity())
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMySignsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }





}