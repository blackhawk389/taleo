package com.sarah.objectives.data.photos

import com.google.gson.annotations.SerializedName

data class Photos(@SerializedName("photoItem") var photoItems: ArrayList<PhotosItem>)