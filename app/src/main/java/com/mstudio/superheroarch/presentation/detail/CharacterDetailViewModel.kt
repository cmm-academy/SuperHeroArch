package com.mstudio.superheroarch.presentation.detail

import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.presentation.model.TheMovieDbEpisode
import com.mstudio.superheroarch.presentation.network.NetworkManager
import com.mstudio.superheroarch.usecase.CharacterAndEpisodeData
import com.mstudio.superheroarch.usecase.GetCharacterAndEpisodeUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val view: CharacterDetailViewTranslator
) {

    private val useCase = GetCharacterAndEpisodeUseCase()

    fun onCharacterReceived(character: CharacterData) {
        if (NetworkManager.isInternetConnection()) {
            getFirstEpisode(character)
        } else {
            view.showNoInternetConnection()
        }
    }

    private fun getFirstEpisode(character: CharacterData) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = useCase.getCharacterAndEpisode(character)
                withContext(Dispatchers.Main) {
                    response?.let { result ->
                        view.showEpisode(result)
                        checkEpisodeExtraDataAvailability(result.firsEpisode.episodeExtraData)
                    } ?: view.showEpisodeError()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showEpisodeError()
                }
            }
        }
    }

    private fun checkEpisodeExtraDataAvailability(episodeExtraData: TheMovieDbEpisode?) {
        episodeExtraData?.let {
            view.showEpisodeExtraData(it)
        } ?: view.showEpisodeExtraDataError()
    }
}

interface CharacterDetailViewTranslator {
    fun showEpisode(characterAndEpisodeData: CharacterAndEpisodeData)
    fun showEpisodeError()
    fun showNoInternetConnection()
    fun showEpisodeExtraData(episodeExtraData: TheMovieDbEpisode)
    fun showEpisodeExtraDataError()
}