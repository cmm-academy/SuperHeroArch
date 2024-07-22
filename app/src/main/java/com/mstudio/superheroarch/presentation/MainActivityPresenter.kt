package com.mstudio.superheroarch.presentation

import com.mstudio.superheroarch.presentation.MainActivityViewModelHelper.changeMainTitle
import com.mstudio.superheroarch.presentation.MainActivityViewModelHelper.setUpViewModel

class MainActivityPresenter(
    private val view: MainActivityViewContract
) {

    fun onCreate() {
        view.setUpMainActvityViewModel(setUpViewModel())
    }

    fun onMainButtonClicked() {
        view.setUpMainActvityViewModel(changeMainTitle())
    }

}

interface MainActivityViewContract {
    fun setUpMainActvityViewModel(mainActivityViewModel: MainActivityViewModel)
}