package com.example.novnavex.Calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.novnavex.R
import com.example.novnavex.Calculator.ResultFragmentArgs
import com.example.novnavex.databinding.FragmentResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)

        // Firestore ve Firebase Auth başlat
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ResultFragment'e gönderilen günlük kalori değerini al
        val daily_calorie = arguments?.let {
            ResultFragmentArgs.fromBundle(it).dailyCalories
        }

        // Günlük kalori ihtiyacını TextView'e yazdır
        binding.textDailyCalories.text = "Your daily calorie need is: $daily_calorie kcal"

        // Save butonuna tıklama işlemi
        binding.buttonSave.setOnClickListener {
            // Kullanıcı ID'sini al
            val userId = auth.currentUser?.uid

            if (userId == null) {
                Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (daily_calorie == null) {
                Toast.makeText(requireContext(), "Daily calorie is not calculated yet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Günlük kalori değerini kaydetmek için veriyi hazırla
            val userData = mapOf(
                "daily_calorie" to daily_calorie,
                "timestamp" to System.currentTimeMillis()
            )

            // Firestore'da kullanıcı dokümanını güncelle
            db.collection("users").document(userId)
                .set(userData, SetOptions.merge()) // Mevcut veriyi koruyarak günceller
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Calories saved successfully!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed to save: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}