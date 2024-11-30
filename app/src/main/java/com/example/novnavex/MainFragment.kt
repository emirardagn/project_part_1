package com.example.novnavex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.novnavex.databinding.FragmentMainBinding
import com.example.novnavex.viewmodel.UserViewModel

class MainFragment :Fragment(){

    private lateinit var binding: FragmentMainBinding

    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentMainBinding>(inflater, R.layout.fragment_main, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.logInButton.setOnClickListener {


            var playerName = binding.textInputLayout.editText?.text.toString()
            view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToCalculatorFragment(playerName))
            //view.findNavController().navigate(R.id.action_mainFragment_to_gameFragment)
        }
    }
}