package com.mstudio.superheroarch

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen)

        val character = intent.getSerializableExtra(MainActivity.EXTRA_CHARACTER) as? Character
        val episode = intent.getSerializableExtra(MainActivity.EXTRA_EPISODE) as? Episode

        val characterNameTextView: TextView = findViewById(R.id.character_name)
        val characterStatusTextView: TextView = findViewById(R.id.character_status)
        val backButton: ImageButton = findViewById(R.id.back)
        val characterImageView: ImageView = findViewById(R.id.character_image)
        val characterLocationTextView: TextView = findViewById(R.id.location)
        val characterOriginTextView: TextView = findViewById(R.id.origin)
        val firstEpisodeTextView: TextView = findViewById(R.id.first_episode)
        val firstEpisodeDateTextView: TextView = findViewById(R.id.first_episode_date)

        backButton.setOnClickListener {
            finish()
        }

        character?.let {
            characterNameTextView.text = it.name
            characterStatusTextView.text = it.status
            characterLocationTextView.text = it.location.name
            characterOriginTextView.text = it.origin.name
            Picasso.get().load(it.image).placeholder(R.drawable.placeholder).error(R.drawable.error)
                .into(characterImageView)
        }

        episode?.let {
            firstEpisodeTextView.text = it.episode
            firstEpisodeDateTextView.text = it.air_date
        }
    }
}
