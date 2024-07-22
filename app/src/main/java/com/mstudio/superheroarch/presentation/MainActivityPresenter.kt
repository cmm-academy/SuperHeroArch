package com.mstudio.superheroarch.presentation

import com.mstudio.superheroarch.presentation.MainActivityViewModelHelper.changeMainTitle
import com.mstudio.superheroarch.presentation.MainActivityViewModelHelper.setUpViewModel

class MainActivityPresenter(
    private val view: MainActivityViewContract
) {

    fun onCreate() {
        view.setUpViewModel(setUpViewModel())
    }

    fun onMainButtonClicked() {
        view.changeMainTitle(changeMainTitle())
    }

}

interface MainActivityViewContract {
    fun setUpViewModel(viewModel: MainActivityViewModel)
    fun changeMainTitle(newTitle: String)
}