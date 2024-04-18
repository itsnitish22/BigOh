package com.nitishsharma.bigoh.main.home.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.nitishsharma.bigoh.BR
import com.nitishsharma.bigoh.R
import com.nitishsharma.bigoh.main.home.callback.HomeItemClickCallback

@EpoxyModelClass
abstract class EpoxyHomeMovieModel : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var imageUrl: String = ""

    @EpoxyAttribute
    var authorName: String = ""

    @EpoxyAttribute
    var imageId: Int = -1

    @EpoxyAttribute
    lateinit var callback: HomeItemClickCallback

    override fun getDefaultLayout(): Int {
        return R.layout.recycler_home_item
    }

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.imageUrl, imageUrl)
        binding.setVariable(BR.imageId, imageId)
        binding.setVariable(BR.authorName, authorName)
        binding.setVariable(BR.callback, callback)
    }
}

@EpoxyModelClass
abstract class EpoxyLoadingModel : DataBindingEpoxyModel() {
    override fun getDefaultLayout(): Int {
        return R.layout.item_pagination_loader
    }
}
