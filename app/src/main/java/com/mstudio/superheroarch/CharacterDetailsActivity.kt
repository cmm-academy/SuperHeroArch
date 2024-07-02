package com.mstudio.superheroarch

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mstudio.superheroarch.data.Character
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class CharacterDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_character_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val character = intent.getParcelableExtra<Character>("CHARACTER")

        val characterName: TextView = findViewById(R.id.character_details_name)
        val characterStatus: TextView = findViewById(R.id.character_details_status)
        val characterSpecies: TextView = findViewById(R.id.character_details_species)
        val characterLocation: TextView = findViewById(R.id.character_details_location)
        val characterOrigin: TextView = findViewById(R.id.character_details_origin)
        val characterImage: ImageView = findViewById(R.id.character_details_image)

        characterName.text = character?.name
        characterStatus.text = character?.status
        characterSpecies.text = character?.species
        characterLocation.text = character?.location?.name ?: "Unknown"
        characterOrigin.text = character?.origin?.name ?: "Unknown"
        Picasso.get().load(character?.image).into(characterImage)

    }
}