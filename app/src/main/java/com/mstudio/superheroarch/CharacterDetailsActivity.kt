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
            intent.getParcelableExtra("character", Character::class.java)
        } else {
            @Suppress("DEPRECATION")
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

        viewModel.onCharacterRetrieved(character)
    }

    override fun showEpisodeDetails(episode: Episode) {
        runOnUiThread {
            findViewById<TextView>(R.id.detailsFirstEpisodeTextView).text = getString(R.string.episode_placeholder, episode.episode, episode.name)
            findViewById<TextView>(R.id.detailsAirDateTextView).text = episode.air_date
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
        fun newIntent(mainActivity: MainActivity, character: Character): Intent {
            return Intent(mainActivity, CharacterDetailsActivity::class.java).run {
                putExtra("character", character)
            }
        }
    }
}