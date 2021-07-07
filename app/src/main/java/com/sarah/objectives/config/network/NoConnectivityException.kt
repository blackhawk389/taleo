package com.sarah.objectives.config.network

import java.lang.Exception


class NoConnectivityException : Exception() {

    override val message
        get() = "Please check your network connection"
}