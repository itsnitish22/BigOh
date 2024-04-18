package com.nitishsharma.bigoh.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nitishsharma.bigoh.databinding.FragmentHomeBinding
import com.nitishsharma.bigoh.main.home.callback.HomeItemClickCallback
import com.nitishsharma.bigoh.main.home.epoxy.HomeEpoxyController
import com.nitishsharma.bigoh.utils.scroll.ScrollCallback
import com.nitishsharma.bigoh.utils.swiperefresh.SwipeRefreshCallback
import com.nitishsharma.bigoh.utils.utils.EventObserver
import com.nitishsharma.bigoh.utils.utils.navigate
import com.nitishsharma.bigoh.utils.utils.setupScrollListen
import com.nitishsharma.bigoh.utils.utils.setupSwipeRefresh
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ScrollCallback, SwipeRefreshCallback {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private val homeEpoxyController: HomeEpoxyController by lazy {
        val callback = HomeItemClickCallback(viewModel)
        HomeEpoxyController(callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentHomeBinding.inflate(layoutInflater, container, false).also {
        binding = it
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        binding.swipRefresh.setupSwipeRefresh(this)
        binding.recylerHome.setupScrollListen(this)
        homeEpoxyController.setFilterDuplicates(true)
        binding.recylerHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeEpoxyController.adapter
        }
    }

    private fun initObservers() {
        viewModel.imageList.observe(viewLifecycleOwner) {
            homeEpoxyController.setData(it, viewModel.loadingModel.value)
        }
        viewModel.loadingModel.observe(viewLifecycleOwner) {
            homeEpoxyController.setData(viewModel.imageList.value, it)
        }
        viewModel.navDirections.observe(viewLifecycleOwner, EventObserver {
            navigate(it)
        })
    }

    override fun requestNewDataOnScroll() {
        viewModel.getImageListPaginated()
    }

    override fun onForcedRefresh() {
        viewModel.getImageList()
    }
}