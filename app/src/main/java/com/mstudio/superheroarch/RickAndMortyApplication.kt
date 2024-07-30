package com.mstudio.superheroarch

import android.app.Application

class RickAndMortyApplication : Application() {

    companion object {
        lateinit var instance: RickAndMortyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}