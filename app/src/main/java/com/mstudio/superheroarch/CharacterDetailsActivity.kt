package com.mstudio.superheroarch

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class CharacterDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
        val character: Character? = if (android.os.Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("character", Character::class.java)
        } else {
            intent.getSerializableExtra("character") as Character
        }
        character?.let {
            findViewById<TextView>(R.id.detailsNameTextView).text = it.name
            findViewById<TextView>(R.id.detailsSpeciesTextView).text = it.species
            findViewById<TextView>(R.id.detailsGenderTextView).text = it.gender
            findViewById<TextView>(R.id.detailsOriginTextView).text = it.origin.name
            findViewById<TextView>(R.id.detailsLocationTextView).text = it.location.name
            Glide.with(this)
                .load(it.image)
                .circleCrop()
                .into(findViewById(R.id.detailsImageView))
        }
    }

    companion object {
        fun newIntent(mainActivity: MainActivity, character: Character): Intent {
            return Intent(mainActivity, CharacterDetailsActivity::class.java).run {
                putExtra("character", character)
            }
        }
    }
}