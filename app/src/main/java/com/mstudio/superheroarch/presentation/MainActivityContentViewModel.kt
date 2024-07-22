package com.mstudio.superheroarch.presentation

import android.content.Context
import com.mstudio.superheroarch.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class MainActivityContentViewModel(
    val buttonTitle: String,
    val title: String
)

object MainActivityViewModelHelper : KoinComponent {

    fun setUpViewModel(): MainActivityContentViewModel {
        val context by inject<Context>()
        return MainActivityContentViewModel(
            buttonTitle = context.resources.getString(R.string.main_button_title),
            title = context.resources.getString(R.string.main_title_not_pressed)
        )
    }

    fun updateTitle(): String {
        val context by inject<Context>()
        return context.resources.getString(R.string.main_title_pressed)
    }
}