package com.sarah.objectives.data.albums

import com.google.gson.annotations.SerializedName


class Album(@SerializedName("albumItem") var albums: ArrayList<AlbumItem>)