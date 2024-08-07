package com.mstudio.superheroarch.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.presentation.network.NetworkManagerImpl
import com.mstudio.superheroarch.usecase.CharacterAndEpisodeData
import com.mstudio.superheroarch.usecase.GetCharacterAndEpisodeUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val view: CharacterDetailViewTranslator,
    private val useCase: GetCharacterAndEpisodeUseCase = GetCharacterAndEpisodeUseCase(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val networkManager: NetworkManagerImpl = NetworkManagerImpl()
) : ViewModel() {

    fun onCharacterReceived(character: CharacterData) {
        if (networkManager.hasInternetConnection()) {
            getFirstEpisode(character)
        } else {
            view.showNoInternetConnection()
        }
    }

    private fun getFirstEpisode(character: CharacterData) {
        viewModelScope.launch(dispatcher) {
            try {
                val response = useCase.getCharacterAndEpisode(character)
                withContext(Dispatchers.Main) {
                    response?.let { result ->
                        view.showEpisode(result)
                        checkEpisodeExtraDataAvailability(result.episodeData.image, result.episodeData.voteAverage)
                    } ?: view.showEpisodeError()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    view.showEpisodeError()
                }
            }
        }
    }

    private fun checkEpisodeExtraDataAvailability(image: String?, voteAverage: Double?) {
        if (image != null && voteAverage != null) {
            view.showEpisodeExtraData(image, voteAverage)
        } else {
            view.showEpisodeExtraDataError()
        }
    }
}

interface CharacterDetailViewTranslator {
    fun showEpisode(characterAndEpisodeData: CharacterAndEpisodeData)
    fun showEpisodeError()
    fun showNoInternetConnection()
    fun showEpisodeExtraData(image: String, voteAverage: Double)
    fun showEpisodeExtraDataError()
}