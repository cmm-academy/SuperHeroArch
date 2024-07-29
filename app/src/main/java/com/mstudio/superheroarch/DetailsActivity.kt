package com.mstudio.superheroarch

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity(), DetailsViewTranslator {

    private var characterNameTextView: TextView? = null
    private var characterStatusTextView: TextView? = null
    private var characterImageView: ImageView? = null
    private var characterLocationTextView: TextView? = null
    private var characterOriginTextView: TextView? = null
    private var firstEpisodeTextView: TextView? = null
    private var firstEpisodeDateTextView: TextView? = null

    private var viewModel: DetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen)

        viewModel = DetailsViewModel(this)

        characterNameTextView = findViewById(R.id.character_name)
        characterStatusTextView = findViewById(R.id.character_status)
        characterImageView = findViewById(R.id.character_image)
        characterLocationTextView = findViewById(R.id.location)
        characterOriginTextView = findViewById(R.id.origin)
        firstEpisodeTextView = findViewById(R.id.first_episode)
        firstEpisodeDateTextView = findViewById(R.id.first_episode_date)

        val backButton: ImageButton = findViewById(R.id.back)
        backButton.setOnClickListener {
            finish()
        }

        val character = intent.getSerializableExtra(MainActivity.EXTRA_CHARACTER) as? Character
        character?.let {
            viewModel?.fetchCharacterDetails(it)
        }
    }

    override fun displayCharacterDetails(character: Character) {
        characterNameTextView?.text = character.name
        characterStatusTextView?.text = character.status
        characterLocationTextView?.text = character.location.name
        characterOriginTextView?.text = character.origin.name
        Picasso.get().load(character.image).placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(characterImageView)
    }

    override fun displayFirstEpisodeDetails(episode: Episode) {
        firstEpisodeTextView?.text = episode.episode
        firstEpisodeDateTextView?.text = episode.air_date
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
