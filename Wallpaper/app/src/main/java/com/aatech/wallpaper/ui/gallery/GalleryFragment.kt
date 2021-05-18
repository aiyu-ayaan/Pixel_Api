package com.aatech.wallpaper.ui.gallery

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.aatech.wallpaper.R
import com.aatech.wallpaper.data.UnsplashPhoto
import com.aatech.wallpaper.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery),
    UnsplashPhotoAdapter.OnItemClickListener {
    private val viewModel by viewModels<GalleryViewModel>()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)
        val unsplashAdaper = UnsplashPhotoAdapter(this)
        binding.apply {
            recyclerView.apply {
                setHasFixedSize(true)
                itemAnimator = null
                adapter = unsplashAdaper.withLoadStateHeaderAndFooter(
                    header = UnsplashPhotoLoadStateAdapter { unsplashAdaper.retry() },
                    footer = UnsplashPhotoLoadStateAdapter { unsplashAdaper.retry() }
                )
            }
            buttonRetry.setOnClickListener {
                unsplashAdaper.retry()
            }
        }
        viewModel.photos.observe(viewLifecycleOwner) {
            unsplashAdaper.submitData(viewLifecycleOwner.lifecycle, it)
        }
        unsplashAdaper.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error

//                Empty View
                if (loadState.source.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached &&
                    unsplashAdaper.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    if (newText.isEmpty()) {
                        viewModel.searchPhotos(GalleryViewModel.DEFAULT_QUERY)
                    }
                }
                return true
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(photo: UnsplashPhoto) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToDetailsFragment2(photo)
        findNavController().navigate(action)
    }
}