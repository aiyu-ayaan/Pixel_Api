package com.aatec.noticeapp.fragment.DetailFragment

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.aatec.noticeapp.R
import com.aatec.noticeapp.databinding.DetailFragmentBinding
import com.google.android.material.transition.MaterialContainerTransform

class DetailFragment : Fragment(R.layout.detail_fragment) {
    private var _binding: DetailFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val navArg: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragment
            duration = 200L
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(Color.TRANSPARENT)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DetailFragmentBinding.bind(view)
        binding.root.transitionName = navArg.transitionname
        binding.apply {
            webView.apply {
                val body = Html
                    .fromHtml(
                        "<![CDATA[<body style=\"text-align:justify;\">"
                                + (navArg.notice.body)
                                + "</body>]]>", HtmlCompat.FROM_HTML_MODE_LEGACY
                    ).toString()
                loadData(body, "text/html; charset=utf-8", "utf-8")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}