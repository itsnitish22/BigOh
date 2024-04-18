package com.nitishsharma.bigoh.domain.repository

import com.nitishsharma.bigoh.data.models.ImageListItem
import com.nitishsharma.bigoh.utils.utils.result.Result
import javax.inject.Inject

class BigOhApiRepositoryImpl @Inject constructor(
    private val bigOhRemoteRepository: BigOhRemoteRepository
): BigOhApiRepository {
    override suspend fun getImagesList(page: Int): Result<ArrayList<ImageListItem>> {
        return bigOhRemoteRepository.getImageList(page)
    }
}