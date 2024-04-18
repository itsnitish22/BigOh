package com.nitishsharma.bigoh.utils.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nitishsharma.bigoh.utils.scroll.ScrollCallback
import com.nitishsharma.bigoh.utils.swiperefresh.SwipeRefreshCallback
import timber.log.Timber

fun RecyclerView.scrollTopTop() {
    this.scrollToPosition(0)
}

fun RecyclerView.setupScrollListen(scrollCallback: ScrollCallback) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val totalItemCount = recyclerView.layoutManager?.itemCount
            val visibleItemCount = recyclerView.layoutManager?.childCount
            val firstVisibleItem = when (recyclerView.layoutManager) {
                is LinearLayoutManager -> {
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                }

                is GridLayoutManager -> {
                    (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                }

                else -> {
                    null
                }
            }
            if (visibleItemCount != null && totalItemCount != null && firstVisibleItem != null) {
                if (visibleItemCount + firstVisibleItem >= totalItemCount && firstVisibleItem >= 1) {
                    scrollCallback.requestNewDataOnScroll()
                }
            }
            super.onScrolled(recyclerView, dx, dy)
        }
    })
}

fun SwipeRefreshLayout.setupSwipeRefresh(callback: SwipeRefreshCallback) {
    this.setOnRefreshListener {
        this.isRefreshing = true
        callback.onForcedRefresh()
    }
}

fun Fragment.navigate(navDirections: NavDirections) {
    try {
        findNavController().navigate(navDirections)
    } catch (exc: Exception) {
        exc.message?.let { Timber.e(it) }
    }
}