package com.mstudio.superheroarch

import android.app.Application

class RickAndMortyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: RickAndMortyApplication
            private set
    }
}