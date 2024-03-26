package com.sign.led.ui.mysigns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.sign.led.databinding.FragmentMySignsBinding
import com.sign.led.ui.mysigns.Adapter.MySignsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MySignsFragment : Fragment() {


    private var _binding : FragmentMySignsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:MySignsAdapter
    private val mySignsViewModel:MySignsViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        mySignsViewModel.getSigns()
        initList()
        initUIState()
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



    }

    private fun loadingState() {
        binding.pbMySigns.isVisible = true
        
    }

    private fun initialState() {
        binding.pbMySigns.isVisible = false


    }

    private fun errorState() {
        binding.pbMySigns.isVisible = false

    }

    private fun initList() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = MySignsAdapter()

        binding.apply {
            rvMySigns.layoutManager = LinearLayoutManager(rvMySigns.context)
            rvMySigns.adapter = adapter
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMySignsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}