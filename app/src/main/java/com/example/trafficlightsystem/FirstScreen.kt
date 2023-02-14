package com.example.trafficlightsystem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.trafficlightsystem.databinding.FragmentFirstScreenBinding

class FirstScreen : Fragment() {

    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            navigateToNextScreen(view)
        }
    }

    private fun navigateToNextScreen(view: View) {
        //get input
        val etCarModel = binding.etCarModel
        val carModel = etCarModel.text.toString().trim()

        //validate input
        if (carModel.isBlank()) {
            etCarModel.error = "Your car model is empty! Please enter a model!"
            return
        } else if (carModel.isNotBlank() && carModel.length < 3) {
            etCarModel.error = "Car model must have at least 3 characters"
            return
        } else {
            val action = FirstScreenDirections.actionFirstScreenToSecondScreen(carModel)
            navController = Navigation.findNavController(view)
            navController.navigate(action)
        }
        etCarModel.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}