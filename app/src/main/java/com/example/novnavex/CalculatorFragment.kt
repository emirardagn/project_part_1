package com.example.novnavex

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.novnavex.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.activity_levels,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerActivity.adapter = adapter

        val userName = arguments?.let {
            CalculatorFragmentArgs.fromBundle(it).userName
        }
        binding.greetingTextView.text = "Hello, $userName!"


        binding.buttonCalculate.setOnClickListener {
            val weight = binding.editWeight.text.toString().toDoubleOrNull()
            val height = binding.editHeight.text.toString().toDoubleOrNull()
            val age = binding.editAge.text.toString().toIntOrNull()
            val activityLevel = when (binding.spinnerActivity.selectedItemPosition) {
                0 -> 1.2
                1 -> 1.375
                2 -> 1.55
                3 -> 1.725
                4 -> 1.9
                else -> 1.2
            }
            val isMale = binding.radioGroupGender.checkedRadioButtonId == binding.radioMale.id

            if (weight == null || height == null || age == null) {
                binding.textTitle.text = "Please fill in all fields"
                return@setOnClickListener
            }

            val bmr = if (isMale) {
                (10 * weight) + (6.25 * height) - (5 * age) + 5
            } else {
                (10 * weight) + (6.25 * height) - (5 * age) - 161
            }

            val dailyCalories = bmr * activityLevel

            val action = CalculatorFragmentDirections.actionCalculatorFragmentToResultFragment(dailyCalories.toFloat())
            findNavController().navigate(action)
        }
    }
}
