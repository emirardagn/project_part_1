package com.example.novnavex.Calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.novnavex.Calculator.CalculatorFragmentDirections
import com.example.novnavex.R
import com.example.novnavex.databinding.FragmentCalculatorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CalculatorFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_calculator, container, false)

        // Firestore ve Firebase Auth başlat
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

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

        // Kullanıcı bilgilerini Firestore'dan çek
        fetchUserFirstName()

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

            val action =
                CalculatorFragmentDirections.actionCalculatorFragmentToResultFragment(dailyCalories.toFloat())
            findNavController().navigate(action)
        }
    }

    private fun fetchUserFirstName() {
        // Kullanıcı ID'sini Firebase Authentication'dan al
        val userId = auth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            binding.greetingTextView.text = "Hello, Guest!"
            return
        }

        // Firestore sorgusu
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val firstName = document.getString("firstName") ?: "Guest"
                    binding.greetingTextView.text = "Hello, $firstName!"
                } else {
                    binding.greetingTextView.text = "Hello, Guest!"
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to fetch user data: ${e.message}", Toast.LENGTH_SHORT).show()
                binding.greetingTextView.text = "Hello, Guest!"
            }
    }
}