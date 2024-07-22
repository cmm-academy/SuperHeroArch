package com.mstudio.superheroarch.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mstudio.superheroarch.databinding.MainActivityBinding
import org.koin.android.ext.android.inject
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainActivityViewContract, KoinComponent {

    private lateinit var binding: MainActivityBinding
    private val presenter: MainActivityPresenter by inject { parametersOf(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.onCreate()
        binding.mainButton.setOnClickListener {
            presenter.onMainButtonClicked()
        }
    }

    override fun setUpMainActvityViewModel(mainActivityViewModel: MainActivityContentViewModel) {
        with(binding) {
            with(mainActivityViewModel) {
                mainButton.text = buttonTitle
                mainTitle.text = title
            }
        }
    }

    override fun updateTitle(newTitle: String) {
        binding.mainTitle.text = newTitle
    }
}