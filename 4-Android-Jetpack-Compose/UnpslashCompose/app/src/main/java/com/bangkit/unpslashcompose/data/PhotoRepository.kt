package com.bangkit.unpslashcompose.data

import com.bangkit.unpslashcompose.model.FakePhotoDataSource
import com.bangkit.unpslashcompose.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PhotoRepository {

    private val photos = mutableListOf<Photo>()

    init {
        if (photos.isEmpty()) {
            FakePhotoDataSource.dummyPhotos.forEach {
                photos.add(Photo(it.id, it.imageUrl, it.name, it.etalase, it.description))
            }
        }
    }

    fun getAllPhotos(): Flow<List<Photo>> {
        return flowOf(photos)
    }

    fun getPhotoById(photoId: Long): Photo {
        return photos.first {
            it.id == photoId
        }
    }

    companion object {
        @Volatile
        private var instance: PhotoRepository? = null

        fun getInstance(): PhotoRepository =
            instance ?: synchronized(this) {
                PhotoRepository().apply {
                    instance = this
                }
            }
    }
}