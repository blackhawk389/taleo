package com.sarah.objectives.features.services.model

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Services(
    @DrawableRes var serviceImage: Int,
     var serviceName: String,
     var serviceDescription: String
):Serializable