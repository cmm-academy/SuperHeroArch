package com.mstudio.superheroarch.presentation.injection

import android.content.Context
import com.mstudio.superheroarch.presentation.MainActivityPresenter
import com.mstudio.superheroarch.presentation.MainActivityViewContract
import org.koin.dsl.module

object PresentationModules {
    val module = module {
        factory { (view: Context) -> MainActivityPresenter(view as MainActivityViewContract) }
    }
}