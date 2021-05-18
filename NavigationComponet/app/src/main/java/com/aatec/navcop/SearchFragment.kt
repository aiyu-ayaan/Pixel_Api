package com.aatec.navcop

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aatec.navcop.databinding.FragmentSearchBinding
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward= */ false)
    }

    private val link =
        "https://stackoverflow.com/questions/35967707/fit-webview-content-to-screen"

    private var savedViewInstance: View? = null

    private lateinit var binding: FragmentSearchBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        binding.apply {
            webView.apply {
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.domStorageEnabled = true
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
                settings.loadsImagesAutomatically = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView, url: String) {
                        super.onPageFinished(view, url)
                    }

                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        view.loadUrl(url)
                        return true
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView, newProgress: Int) {
                        progressBar.visibility = View.VISIBLE
                        progressBar.progress = newProgress
                        if (newProgress == 100) {
                            progressBar.visibility = View.GONE
                        }
                        super.onProgressChanged(view, newProgress)
                    }
                }
                if (savedInstanceState != null) {
                    binding.webView.restoreState(savedInstanceState)
                } else {
                    loadUrl(link)
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (binding.webView != null) {
            binding.webView.saveState(outState)
        }
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(requireContext(), "Destroy", Toast.LENGTH_SHORT).show()
    }
}