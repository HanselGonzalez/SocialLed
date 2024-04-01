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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initList()
        initUIState()
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

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}