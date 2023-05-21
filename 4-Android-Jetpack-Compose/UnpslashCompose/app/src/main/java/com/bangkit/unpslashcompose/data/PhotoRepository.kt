package com.bangkit.unpslashcompose.data

class PhotoRepository {

    fun getAllPhotos() {

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