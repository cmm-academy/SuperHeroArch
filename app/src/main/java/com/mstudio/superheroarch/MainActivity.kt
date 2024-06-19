package com.mstudio.superheroarch

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val varButton = findViewById<Button>(R.id.button)
        val varText = findViewById<TextView>(R.id.title)
        val varNewText = "Texto cambiado"
        varButton.setOnClickListener {
            Log.d("tag", "Botón pulsado")
            varText.setText(varNewText)
        }
    }
}