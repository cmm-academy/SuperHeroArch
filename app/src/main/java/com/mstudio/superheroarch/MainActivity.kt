package com.mstudio.superheroarch

import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        val mainButton : Button = findViewById(R.id.main_button)
        val mainText = findViewById<TextView>(R.id.title)

        mainButton.setOnClickListener{
            val handler = Handler()
            val runnable = Runnable {
                mainText.text = getString(R.string.main_title)
            }
            mainText.setText(getString(R.string.main_text_clicked))
            handler.postDelayed(runnable, 3000)
        }

    }
}