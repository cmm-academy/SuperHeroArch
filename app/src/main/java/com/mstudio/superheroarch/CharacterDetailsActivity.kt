package com.mstudio.superheroarch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class CharacterDetailsActivity : AppCompatActivity(), CharacterDetailsViewTranslator {

    private val viewModel = CharacterDetailsViewModel(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
        val character: Character? = if (android.os.Build.VERSION.SDK_INT >= 33) {
            intent.getSerializableExtra(CHARACTER_INTENT_KEY, Character::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(CHARACTER_INTENT_KEY) as Character
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

        viewModel.onCharacterRetrieved(character)
    }

    override fun showEpisodeDetails(episode: Episode) {
        findViewById<TextView>(R.id.detailsFirstEpisodeTextView).text = getString(R.string.episode_placeholder, episode.episodeNumber, episode.name)
        findViewById<TextView>(R.id.detailsAirDateTextView).text = episode.releaseDate
    }

    override fun showEpisodeDetails(episode: TMDBEpisodeData) {
        findViewById<TextView>(R.id.detailsRatingTextView).text = episode.rating.toString()
        Glide.with(this)
            .load(episode.imagePath)
            .into(findViewById(R.id.detailsHeaderImage))
    }

    override fun showErrorMessage(error: String) {
        Snackbar.make(findViewById(android.R.id.content), error, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoader() {
        findViewById<ProgressBar>(R.id.detailsProgressBar).visibility = View.VISIBLE
    }

    override fun hideLoader() {
        findViewById<ProgressBar>(R.id.detailsProgressBar).visibility = View.GONE
    }

    companion object {
        const val CHARACTER_INTENT_KEY = "character"
        fun newIntent(mainActivity: MainActivity, character: Character): Intent {
            return Intent(mainActivity, CharacterDetailsActivity::class.java).run {
                putExtra(CHARACTER_INTENT_KEY, character)
            }
        }
    }
}