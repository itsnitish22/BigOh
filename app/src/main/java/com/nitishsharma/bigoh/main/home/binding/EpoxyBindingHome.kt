package com.nitishsharma.bigoh.main.home.binding

import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nitishsharma.bigoh.R
import com.nitishsharma.bigoh.utils.common.LoadingModel
import com.nitishsharma.bigoh.utils.common.LoadingState

@BindingAdapter("urlToImage")
fun loadImageTopRoundedCorner(imageView: AppCompatImageView, imageUrl: String?) {
    if (imageUrl != null) {
        Glide.with(imageView.context).load(imageUrl)
            .skipMemoryCache(true)
            .error(
                Glide.with(imageView.context)
                    .load(
                        ContextCompat.getDrawable(
                            imageView.context,
                            R.drawable.movie_dummy_img_horizontal
                        )
                    )
            )
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)

    } else {
        Glide.with(imageView.context)
            .load(
                ContextCompat.getDrawable(
                    imageView.context,
                    R.drawable.movie_dummy_img_horizontal
                )
            )
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView)
    }
}

@BindingAdapter("updateSwipeRefresh")
fun updateSwipeRefresh(view: SwipeRefreshLayout, loadingObj: LoadingModel?) {
    if (loadingObj?.loadingModel != LoadingState.LOADING) {
        view.isRefreshing = false
    }
}