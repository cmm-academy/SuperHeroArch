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

        viewModel.onCreate(character)
    }

    override fun showCharacterInformation(fullCharacterInformation: CharacterUIEntity) {
        with(fullCharacterInformation) {
            findViewById<TextView>(R.id.detailsNameTextView).text = name
            findViewById<TextView>(R.id.detailsSpeciesTextView).text = species
            findViewById<TextView>(R.id.detailsGenderTextView).text = gender
            findViewById<TextView>(R.id.detailsOriginTextView).text = origin
            findViewById<TextView>(R.id.detailsLocationTextView).text = location
            findViewById<TextView>(R.id.detailsFirstEpisodeTextView).text = getString(R.string.episode_placeholder, firstEpisode.episodeNumber, firstEpisode.name)
            findViewById<TextView>(R.id.detailsAirDateTextView).text = firstEpisode.releaseDate
            findViewById<TextView>(R.id.detailsRatingTextView).text = firstEpisode.rating.toString()
            val glideInstance = Glide.with(this@CharacterDetailsActivity)
            glideInstance
                .load(firstEpisode.imagePath)
                .into(findViewById(R.id.detailsHeaderImage))
            glideInstance
                .load(image)
                .circleCrop()
                .into(findViewById(R.id.detailsImageView))
        }
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