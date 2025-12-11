package com.mstudio.superheroarch

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setClickListener()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setClickListener() {
        val myButton = findViewById<Button>(R.id.button)
        myButton.setOnClickListener {
            getCharacters()
        }
    }

    private fun getCharacters() {
        val buttonText = findViewById<TextView>(R.id.buttonText)
        buttonText.text = "Characters are being loaded..."
        lifecycleScope.launch {
            try {
                val response = RetroFitClient.apiService.getCharacters()
                val firstCharacterName = response
                buttonText.text = firstCharacterName.toString()
            } catch (e: Exception) {
                buttonText.text = "It does not work due to this error: ${e.message}"
            }
        }
    }
}
