package com.aatec.navcop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aatec.navcop.databinding.FragmentHomeBinding
import com.google.android.material.transition.MaterialSharedAxis

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        binding.apply {
            buttonLogin.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToLogInFragment()
                findNavController().navigate(action)
            }
        }
    }
}