package com.mstudio.superheroarch.presentation.detail

import com.mstudio.superheroarch.remotedatasource.model.CharactersRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.EpisodeRemoteEntity
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val view: CharacterDetailViewTranslator
) {

    private val repository = RickAndMortyRepository()
    fun onCharacterReceived(character: CharactersRemoteEntity) {
        val firstEpisode = character.episode.first().split("/").last()
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getSingleEpisode(firstEpisode.toInt())
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