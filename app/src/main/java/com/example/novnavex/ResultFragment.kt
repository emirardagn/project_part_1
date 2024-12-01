package com.example.novnavex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.novnavex.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dailyCalories = arguments?.let {
            ResultFragmentArgs.fromBundle(it).dailyCalories
        }

        binding.textDailyCalories.text = "Your daily calorie need is: $dailyCalories kcal"

        binding.buttonRecalculate.setOnClickListener {
            findNavController().navigate(R.id.action_ResultFragment_to_CalculatorFragment)
        }
    }
}
