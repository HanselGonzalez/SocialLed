package com.sign.led.ui.signs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.sign.led.databinding.FragmentSignsBinding
import com.sign.led.ui.signs.Adapter.SignsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignsFragment : Fragment() {

    private var _binding : FragmentSignsBinding? = null
    private val binding get() = _binding!!
    private val signsViewModel:SignsViewModel by viewModels()
    private lateinit var adapter:SignsAdapter
    private var adCount = 0
    private var interstitial: InterstitialAd? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initList()
        initAds()
        initListeners()
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

    private fun initUIState() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                signsViewModel.signsP.collect(){
                    Log.i("etcetera","$it")
                    adapter.updateData(it)
                }
            }
        }

    }

    private fun initList() {
        adapter = SignsAdapter(navigateToFullView = { navigateToFullView(it) })


        binding.apply {
            rvLedSigns.layoutManager = LinearLayoutManager(rvLedSigns.context)
            rvLedSigns.adapter = adapter
        }
    }

    private fun navigateToFullView(idItem:Long){

        findNavController().navigate(SignsFragmentDirections.actionSignsFragment2ToSignFullViewActivity(idItem,"signProvider"))
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
        _binding = FragmentSignsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}