package com.mstudio.superheroarch.presentation.detail

import com.mstudio.superheroarch.remotedatasource.api.RetrofitInstance
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.EpisodeRemoteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val view: CharacterDetailViewTranslator
) {

    fun onCharacterReceived(character: CharactersRemoteEntity) {
        val firstEpisode = character.episode.first().split("/").last()
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.retrofit().create(RickAndMortyApi::class.java).getSingleEpisode(firstEpisode.toInt())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        view.showEpisode(it)
                    }
                } else {
                    view.showEpisodeError()
                }
            }
        }
    }
}

interface CharacterDetailViewTranslator {
    fun showEpisode(episode: EpisodeRemoteEntity)
    fun showEpisodeError()
}