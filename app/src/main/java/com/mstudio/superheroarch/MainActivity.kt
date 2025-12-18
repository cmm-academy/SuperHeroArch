package com.mstudio.superheroarch

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView


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
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = CharacterAdapter(emptyList())

    }

    private fun setClickListener() {
        val myButton = findViewById<Button>(R.id.button)
        myButton.setOnClickListener {
            it.visibility = View.GONE
            getCharacters()
        }
    }

    private fun getCharacters() {

        lifecycleScope.launch {
            try {
                val response = RetroFitClient.apiService.getCharacters()
                val adaptor = CharacterAdapter(response.results)
                println(adaptor)
                adaptor.notifyItemChanged(response.results.size-1)
                recyclerView.adapter = adaptor



            } catch (e: Exception) {
                val errorMessage = "Error: ${e.message}"
                println(errorMessage)
            }
        }
    }
}
