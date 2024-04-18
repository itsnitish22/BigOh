package com.nitishsharma.bigoh.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nitishsharma.bigoh.application.common.CommonViewModel
import com.nitishsharma.bigoh.data.models.ImageListItem
import com.nitishsharma.bigoh.domain.repository.BigOhApiRepositoryImpl
import com.nitishsharma.bigoh.utils.common.LoadingModel
import com.nitishsharma.bigoh.utils.common.LoadingState
import com.nitishsharma.bigoh.utils.utils.result.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repositoryImpl: BigOhApiRepositoryImpl
) : CommonViewModel() {

    var pageNumber: Int = 2
    private val _imageList: MutableLiveData<ArrayList<ImageListItem>> = MutableLiveData()
    val imageList: LiveData<ArrayList<ImageListItem>> get() = _imageList

    init {
        getImageList()
    }

    fun getImageList() {
        viewModelScope.launch {
            updateLoadingModel(LoadingModel(LoadingState.LOADING, null, isListEmpty()))
            val response = repositoryImpl.getImagesList(1)
            if (response is Result.Success) {
                _imageList.postValue(response.data)
                pageNumber = 2
                updateLoadingModel(LoadingModel(LoadingState.COMPLETED, null, isListEmpty()))

            } else if (response is Result.Error) {
                updateLoadingModel(
                    LoadingModel(
                        LoadingState.ERROR,
                        response.exception,
                        isListEmpty()
                    )
                )
            }
        }
    }

    fun getImageListPaginated() {
        if (loadingModel.value?.loadingModel != LoadingState.LOADING) {
            updateLoadingModel(LoadingModel(LoadingState.LOADING, null, isListEmpty()))
            viewModelScope.launch {
                val response = repositoryImpl.getImagesList(pageNumber)
                if (response is Result.Success) {
                    appendData(response.data)
                    pageNumber++
                    updateLoadingModel(LoadingModel(LoadingState.COMPLETED, null, isListEmpty()))
                } else if (response is Result.Error) {
                    updateLoadingModel(
                        LoadingModel(
                            LoadingState.ERROR,
                            response.exception,
                            isListEmpty()
                        )
                    )
                }
            }
        }
    }

    private fun appendData(data: java.util.ArrayList<ImageListItem>) {
        val previousData = _imageList.value
        previousData?.let {
            it.addAll(data)
            _imageList.postValue(it)
        }
    }

    fun onImageClick(authorName: String, imageUrl: String) {
        updateNavDirection(
            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                imageUrl,
                authorName
            )
        )
    }

    private fun isListEmpty() = _imageList.value.isNullOrEmpty()

}