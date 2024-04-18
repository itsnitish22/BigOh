package com.nitishsharma.bigoh.main.home.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import com.nitishsharma.bigoh.data.models.ImageListItem
import com.nitishsharma.bigoh.main.home.callback.HomeItemClickCallback
import com.nitishsharma.bigoh.utils.common.LoadingModel
import com.nitishsharma.bigoh.utils.common.LoadingState
import timber.log.Timber

class HomeEpoxyController(private val itemClickCallback: HomeItemClickCallback) :
    Typed2EpoxyController<ArrayList<ImageListItem>, LoadingModel>() {
    override fun buildModels(data: ArrayList<ImageListItem>?, loadingModel: LoadingModel?) {
        data?.let {
            it.forEachIndexed { index, imageListItem ->
                epoxyHomeMovie {
                    id("image$index")
                    imageId(imageListItem.id?.toInt() ?: -1)
                    imageUrl(imageListItem.downloadUrl ?: "")
                    authorName(imageListItem.author ?: "")
                    callback(this@HomeEpoxyController.itemClickCallback)
                }
            }
            if (loadingModel?.loadingModel == LoadingState.LOADING && !loadingModel.isListEmpty) {
                EpoxyLoadingModel_()
                    .id("loading")
            }
        }
    }
}