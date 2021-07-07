package com.sarah.objectives.callbacks

import com.sarah.objectives.features.services.model.Services


interface ServiceListener {

    fun onServiceClicked(data: Services)
}