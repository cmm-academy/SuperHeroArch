package com.mstudio.superheroarch.ui.activity

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mstudio.superheroarch.data_remote.ApiRick
import com.mstudio.superheroarch.data_remote.ApiService
import com.mstudio.superheroarch.data_local.AppDatabase
import com.mstudio.superheroarch.repository.CharacterEntity
import com.mstudio.superheroarch.repository.EpisodeEntity
import com.mstudio.superheroarch.data_local.LocalDataSourceImpl
import com.mstudio.superheroarch.R
import com.mstudio.superheroarch.data_remote.RemoteDataSourceImpl
import com.mstudio.superheroarch.data_remote.TmdbApi
import com.mstudio.superheroarch.data_remote.TmdbApiService
import com.mstudio.superheroarch.data_remote.TmdbRemoteDataSourceImpl
import com.mstudio.superheroarch.domain.GetEpisodeDetailsUseCase
import com.mstudio.superheroarch.domain.GetEpisodeTMDBDetailsUseCase
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.presentation.DetailsViewModel
import com.mstudio.superheroarch.presentation.DetailsViewTranslator
import com.mstudio.superheroarch.repository.TmdbRepository
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity(), DetailsViewTranslator {

    private var viewModel: DetailsViewModel? = null

    private var characterNameTextView: TextView? = null
    private var characterStatusTextView: TextView? = null
    private var characterImageView: ImageView? = null
    private var characterLocationTextView: TextView? = null
    private var characterOriginTextView: TextView? = null
    private var firstEpisodeTextView: TextView? = null
    private var firstEpisodeDateTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen)

        val db = AppDatabase.getDatabase(this)
        val apiRick: ApiRick = ApiService.retrofit.create(ApiRick::class.java)
        val apiTmdb: TmdbApi = TmdbApiService.retrofit.create(TmdbApi::class.java)
        val remoteDataSource = RemoteDataSourceImpl(apiRick)
        val localDataSource = LocalDataSourceImpl(db.characterDao())

        val repository = RickAndMortyRepository(remoteDataSource, localDataSource)
        val tmdbRemoteDataSource = TmdbRemoteDataSourceImpl(apiTmdb)
        val tmdbRepository = TmdbRepository(tmdbRemoteDataSource)

        val getEpisodeDetailsUseCase = GetEpisodeDetailsUseCase(repository)
        val getEpisodeTMDBDetailsUseCase = GetEpisodeTMDBDetailsUseCase(tmdbRepository)

        viewModel = DetailsViewModel(this, getEpisodeDetailsUseCase, getEpisodeTMDBDetailsUseCase)

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

        val character = intent.getSerializableExtra(MainActivity.EXTRA_CHARACTER) as? CharacterEntity
        character?.let {
            viewModel?.fetchCharacterDetails(it)
        }
    }

    override fun displayEpisodeRatingAndImage(rating: Double, imageUrl: String?) {
        val ratingTextView: TextView = findViewById(R.id.episode_rating)

        ratingTextView.text = "Rating: $rating"
    }

    override fun displayCharacterDetails(character: CharacterEntity) {
        characterNameTextView?.text = character.name
        characterStatusTextView?.text = character.status
        characterLocationTextView?.text = character.locationName
        characterOriginTextView?.text = character.originName
        Picasso.get().load(character.image).placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(characterImageView)
    }

    override fun displayFirstEpisodeDetails(episode: EpisodeEntity) {
        firstEpisodeTextView?.text = episode.episode
        firstEpisodeDateTextView?.text = episode.air_date
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
