package com.aatec.navcop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aatec.navcop.databinding.FragmentWelcomeBinding
import com.google.android.material.transition.MaterialSharedAxis

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private val args: WelcomeFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentWelcomeBinding.bind(view)
        binding.apply {
            textViewUserName.text = args.userName
            textViewUserPassword.text = args.passWord
            buttonOk.setOnClickListener {
                val action = WelcomeFragmentDirections
                    .actionWelcomeFragmentToHomeFragment()

                findNavController().navigate(action)
            }
        }
    }
}