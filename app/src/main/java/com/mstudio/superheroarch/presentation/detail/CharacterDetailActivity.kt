package com.mstudio.superheroarch.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.databinding.CharacterDetailActivityBinding
import com.mstudio.superheroarch.presentation.overview.MainActivity.Companion.CHARACTER_DATA_KEY
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.EpisodeRemoteEntity

class CharacterDetailActivity : AppCompatActivity(), CharacterDetailViewTranslator {
    private lateinit var binding: CharacterDetailActivityBinding
    private val viewModel: CharacterDetailViewModel by lazy { CharacterDetailViewModel(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CharacterDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val character = intent.getSerializableExtra(CHARACTER_DATA_KEY) as CharactersRemoteEntity
        viewModel.onCharacterReceived(character)

        with(binding) {
            topBar.navigationIcon = ContextCompat.getDrawable(this@CharacterDetailActivity, R.drawable.ic_arrow_left)
            topBar.setNavigationOnClickListener {
                finish()
            }
            topBar.title = character.name
            topBar.isTitleCentered = true
            imageDetail.load(character.image)
            statusDetail.text = resources.getString(R.string.character_status, character.status)
            speciesDetail.text = resources.getString(R.string.character_species, character.species)
            originDetail.text = resources.getString(R.string.character_origin, character.origin.name)
            locationDetail.text = resources.getString(R.string.character_location, character.location.name)
        }
    }

    override fun showEpisode(episode: EpisodeRemoteEntity) {
        with(binding) {
            episodeNumberDetail.visibility = View.VISIBLE
            episodeDateDetail.visibility = View.VISIBLE
            episodeNumberDetail.text = resources.getString(R.string.first_episode_number, episode.episode)
            episodeDateDetail.text = resources.getString(R.string.first_episode_air_date, episode.airDate)
        }
    }

    override fun showEpisodeError() {
        binding.episodeNumberDetail.visibility = View.GONE
        binding.episodeDateDetail.visibility = View.GONE
        Snackbar.make(findViewById(android.R.id.content), resources.getString(R.string.episode_error_message), Snackbar.LENGTH_LONG).show()
    }

    override fun showNoInternetConnection() {
        Snackbar.make(findViewById(android.R.id.content), resources.getString(R.string.no_internet_message), Snackbar.LENGTH_LONG).show()
    }
}