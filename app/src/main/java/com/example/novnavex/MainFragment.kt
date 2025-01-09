package com.example.novnavex

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.novnavex.databinding.FragmentMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.random.Random

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val db = FirebaseFirestore.getInstance() // Firestore instance

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchRandomMotivation()
    }

    private fun fetchRandomMotivation() {
        db.collection("motivations")
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val randomIndex = Random.nextInt(documents.size())
                    val randomMotivation = documents.documents[randomIndex].getString("sentence")

                    binding.quoteTextView.text = randomMotivation ?: "Stay motivated!"
                } else {
                    binding.quoteTextView.text = "Just do it!"
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MainFragment", "Error fetching motivations: ${exception.message}")
                binding.quoteTextView.text = "Error fetching quote. Try again later."
            }
    }
}