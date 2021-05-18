package com.aatec.navcop

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialSharedAxis

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward= */ false)
    }
}