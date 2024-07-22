package com.mstudio.superheroarch.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            mainButton.text = resources.getText(com.mstudio.superheroarch.R.string.main_button_title)
            mainTitle.text = resources.getText(com.mstudio.superheroarch.R.string.main_title_not_pressed)
            mainButton.setOnClickListener {
                binding.mainTitle.text = resources.getText(R.string.main_title_pressed)
            }
        }
    }
}