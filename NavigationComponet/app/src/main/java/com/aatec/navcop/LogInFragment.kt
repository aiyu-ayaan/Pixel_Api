package com.aatec.navcop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aatec.navcop.databinding.FragmentLoginBinding
import com.google.android.material.transition.MaterialSharedAxis

class LogInFragment : Fragment(R.layout.fragment_login) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        binding.apply {
            buttonConfirm.setOnClickListener {
                val userName = editTextUserName.text.toString()
                val passWord = editTextUserPassword.text.toString()
                val action = LogInFragmentDirections.actionLogInFragmentToWelcomeFragment(
                    userName = userName,
                    passWord = passWord
                )
                findNavController().navigate(action)
            }
        }
    }
}