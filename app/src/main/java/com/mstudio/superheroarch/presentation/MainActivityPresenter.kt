package com.mstudio.superheroarch.presentation

import com.mstudio.superheroarch.presentation.MainActivityViewModelHelper.setUpViewModel
import com.mstudio.superheroarch.presentation.MainActivityViewModelHelper.updateTitle

class MainActivityPresenter(
    private val view: MainActivityViewContract
) {

    private var baseViewModel: MainActivityContentViewModel? = null

    fun onCreate() {
        baseViewModel = setUpViewModel()
        baseViewModel?.let {
            view.setUpMainActvityViewModel(it)
        }
    }

    fun onMainButtonClicked() {
        val newTitle = baseViewModel?.copy(title = updateTitle())?.title
        newTitle?.let {
            view.updateTitle(it)
        }
    }

}

interface MainActivityViewContract {
    fun setUpMainActvityViewModel(mainActivityViewModel: MainActivityContentViewModel)
    fun updateTitle(newTitle: String)
}