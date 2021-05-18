package com.aatec.noticeapp.fragment.HomeFragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aatec.noticeapp.NavGraphDirections
import com.aatec.noticeapp.R
import com.aatec.noticeapp.data.Notice
import com.aatec.noticeapp.databinding.HomeFragmentBinding
import com.aatec.noticeapp.utils.DataState
import com.aatec.noticeapp.utils.MainStateListener
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment), HomeAdapter.OnClickListener {
    private var _binding: HomeFragmentBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }


        _binding = HomeFragmentBinding.bind(view)
        val homeAdapter = HomeAdapter(this)

        binding.apply {
            showNotice.apply {
                adapter = homeAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.setStateListener(MainStateListener.GetData)

            viewModel.dataState.observe(viewLifecycleOwner) { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        homeAdapter.submitList(dataState.data)
                    }
                    is DataState.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "${dataState.exception}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    DataState.Loading -> {
//                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setOnClickListener(notice: Notice, view: View) {

        exitTransition = MaterialElevationScale(false).apply {
            duration = 200L
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 200L
        }
        val extras = FragmentNavigatorExtras(view to notice.title)
        val action = NavGraphDirections.actionGlobalDetailFragment(notice, notice.title)
        findNavController().navigate(action, extras)
    }
}