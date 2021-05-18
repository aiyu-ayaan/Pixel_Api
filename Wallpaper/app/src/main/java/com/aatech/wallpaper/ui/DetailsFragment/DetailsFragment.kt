package com.aatech.wallpaper.ui.DetailsFragment

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.aatech.wallpaper.R
import com.aatech.wallpaper.data.UnsplashPhoto
import com.aatech.wallpaper.databinding.FragmentDetailsBinding
import com.aatech.wallpaper.utlis.makeSnackBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val args by navArgs<DetailsFragmentArgs>()
    private lateinit var outStream: OutputStream
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var photo: UnsplashPhoto
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        binding.apply {
            photo = args.photo

            Glide.with(this@DetailsFragment)
                .load(photo!!.urls.full)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewCreator.isVisible = true
                        buttonDownload.isVisible = true
                        textViewDescription.isVisible = photo.description != null
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                })
                .into(imageView)
            textViewDescription.text = photo.description
            val uri = Uri.parse(photo.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            textViewCreator.apply {
                text = "Photo By ${photo.user.name} On Unsplash"
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_download -> {
                download(photo)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun download(photo: UnsplashPhoto) {
        makeSnackBar(
            "Downloading.....😊😊😊 (It will take time based on your Internet Speed)",
            binding.root
        )
        viewLifecycleOwner.lifecycleScope.launch {
            GlobalScope.launch(Dispatchers.IO) {
                val url =
                    URL(photo.links.download)
                val connection: HttpURLConnection =
                    url.openConnection() as HttpURLConnection
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val filepath: File = Environment.getExternalStorageDirectory()
                val dir = File(filepath.absolutePath + "/Demo/")
                dir.mkdir()
                val file = File(dir, photo.id + ".png")
                val out: OutputStream
                try {
                    out = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                    out.flush()
                    out.close()
                    viewLifecycleOwner.lifecycleScope.launchWhenResumed {
                        makeSnackBar("Downloaded", binding.root)
                    }
                } catch (e: Exception) {
                    Log.d("error", "${e.message}")
                }
            }
        }
    }

}