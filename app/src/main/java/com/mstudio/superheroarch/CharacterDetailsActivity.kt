package com.mstudio.superheroarch

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CharacterDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        val character = intent.getSerializableExtra("character") as? Character

        if (character != null) {
            val imageView = findViewById<ImageView>(R.id.characterImageView)
            val nameTextView = findViewById<TextView>(R.id.characterNameTextView)
            val statusTextView = findViewById<TextView>(R.id.characterStatusTextView)

            Glide.with(this).load(character.image).into(imageView)
            nameTextView.text = character.name
            statusTextView.text = character.status
        }
    }
}
