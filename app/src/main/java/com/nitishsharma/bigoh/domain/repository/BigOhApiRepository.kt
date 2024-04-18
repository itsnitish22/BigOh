package com.nitishsharma.bigoh.domain.repository

import com.nitishsharma.bigoh.data.models.ImageListItem
import com.nitishsharma.bigoh.utils.utils.result.Result

interface BigOhApiRepository {
    suspend fun getImagesList(page: Int): Result<ArrayList<ImageListItem>>
}