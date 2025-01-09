package com.example.novnavex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.novnavex.databinding.FragmentMainBinding
import com.example.novnavex.viewmodel.UserViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: UserViewModel by viewModels()  // Get the shared ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Set up the ViewModel and LiveData binding
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Observe the username and update the UI
        viewModel.userName.observe(viewLifecycleOwner, Observer { username ->
            // Display the username in a TextView or any other UI component
            //binding.helloTextView.text = "Hello, $username"  // Assuming you have a TextView with id helloTextView
        })


    }
}
