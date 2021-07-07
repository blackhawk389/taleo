package com.sarah.objectives.callbacks

import com.sarah.objectives.data.photos.PhotosItem


interface onPhotoClickListener {
    fun onPhotoClicked(data:PhotosItem)
}