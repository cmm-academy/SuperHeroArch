package com.mstudio.superheroarch

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.primary_button)
        val text = findViewById<TextView>(R.id.title)

        button.setOnClickListener {
            text.text = getString(R.string.new_text)
        }
    }
}