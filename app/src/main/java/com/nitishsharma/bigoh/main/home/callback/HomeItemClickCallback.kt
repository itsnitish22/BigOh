package com.nitishsharma.bigoh.main.home.callback

import com.nitishsharma.bigoh.main.home.HomeViewModel

class HomeItemClickCallback(private val viewModel: HomeViewModel) {
    fun onImageClick(authorName: String, imageUrl: String) {
        viewModel.onImageClick(authorName, imageUrl)
    }
}