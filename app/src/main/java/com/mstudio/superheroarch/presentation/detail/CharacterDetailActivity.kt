package com.mstudio.superheroarch.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.databinding.CharacterDetailActivityBinding
import com.mstudio.superheroarch.localdatasource.DatabaseHelper
import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.presentation.network.NetworkManagerImpl
import com.mstudio.superheroarch.presentation.overview.MainActivity.Companion.CHARACTER_DATA_KEY
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApiHelper
import com.mstudio.superheroarch.remotedatasource.api.TheMovieDbApiHelper
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.TheMovieDbRepository
import com.mstudio.superheroarch.usecase.CharacterAndEpisodeData
import com.mstudio.superheroarch.usecase.GetCharacterAndEpisodeUseCase
import com.mstudio.superheroarch.usecase.SetFavoriteCharacterUseCase
import kotlinx.coroutines.Dispatchers

class CharacterDetailActivity : AppCompatActivity(), CharacterDetailViewTranslator {
    private lateinit var binding: CharacterDetailActivityBinding
    private val viewModel: CharacterDetailViewModel by lazy {
        CharacterDetailViewModel(
            this,
            GetCharacterAndEpisodeUseCase(
                RickAndMortyRepository(DatabaseHelper.create(), RickAndMortyApiHelper.create()),
                TheMovieDbRepository(TheMovieDbApiHelper.create())
            ),
            Dispatchers.IO,
            NetworkManagerImpl(),
            SetFavoriteCharacterUseCase(RickAndMortyRepository(DatabaseHelper.create(), RickAndMortyApiHelper.create()))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CharacterDetailActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val character = intent.getSerializableExtra(CHARACTER_DATA_KEY) as CharacterData
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
            originDetail.text = resources.getString(R.string.character_origin, character.origin)
            locationDetail.text = resources.getString(R.string.character_location, character.location)
            favoriteIcon.setOnClickListener {
                viewModel.onFavoriteClicked()
            }
        }
    }

    override fun showEpisode(characterAndEpisodeData: CharacterAndEpisodeData) {
        with(binding) {
            episodeNumberDetail.visibility = View.VISIBLE
            episodeDateDetail.visibility = View.VISIBLE
            topDividerFirstEpisode.visibility = View.VISIBLE
            episodeNumberDetail.text = resources.getString(R.string.first_episode_number, characterAndEpisodeData.episodeData.episode)
            episodeDateDetail.text = resources.getString(R.string.first_episode_air_date, characterAndEpisodeData.episodeData.airDate)
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

    override fun showEpisodeExtraData(image: String, voteAverage: Double) {
        with(binding) {
            bottomDividerFirstEpisode.visibility = View.VISIBLE
            scoreIcon.visibility = View.VISIBLE
            episodeScoreDetail.text = voteAverage.toString()
            episodeImage.load(image)
        }
    }

    override fun showEpisodeExtraDataError() {
        Snackbar.make(findViewById(android.R.id.content), resources.getString(R.string.episode_details_error_message), Snackbar.LENGTH_LONG).show()
    }

    override fun showCharacterAsFavorite() {
        binding.favoriteIcon.setImageResource(R.drawable.ic_favorite_selected)
    }

    override fun showCharacterAsNonFavorite() {
        binding.favoriteIcon.setImageResource(R.drawable.ic_favorite_unselected)
    }
}