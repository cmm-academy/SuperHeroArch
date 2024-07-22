package com.mstudio.superheroarch.presentation

import android.content.Context
import com.mstudio.superheroarch.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class MainActivityViewModel(
    val buttonTitle: String,
    val title: String
)

object MainActivityViewModelHelper : KoinComponent {

    fun setUpViewModel(): MainActivityViewModel {
        val context by inject<Context>()
        return MainActivityViewModel(
            buttonTitle = context.resources.getString(R.string.main_button_title),
            title = context.resources.getString(R.string.main_title_not_pressed)
        )
    }

    fun changeMainTitle(): MainActivityViewModel {
        val context by inject<Context>()
        return setUpViewModel().copy(title = context.resources.getString(R.string.main_title_pressed))
    }
}