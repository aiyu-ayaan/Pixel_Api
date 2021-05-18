package com.aatech.wallpaper.Api

import com.aatech.wallpaper.data.UnsplashPhoto

data class UnsplashResponse(
    val results: List<UnsplashPhoto>
)