package com.mstudio.superheroarch.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstudio.superheroarch.presentation.model.CharacterData
import com.mstudio.superheroarch.presentation.network.NetworkManagerImpl
import com.mstudio.superheroarch.usecase.CharacterAndEpisodeData
import com.mstudio.superheroarch.usecase.GetCharacterAndEpisodeUseCase
import com.mstudio.superheroarch.usecase.GetFavCharactersUseCase
import com.mstudio.superheroarch.usecase.SetFavCharacterUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailViewModel(
    private val view: CharacterDetailViewTranslator,
    private val useCase: GetCharacterAndEpisodeUseCase,
    private val dispatcher: CoroutineDispatcher,
    private val networkManager: NetworkManagerImpl,
    private val setFavCharacterUseCase: SetFavCharacterUseCase,
    private val getFavCharactersUseCase: GetFavCharactersUseCase
) : ViewModel() {

    private var characterData: CharacterData? = null

    fun onCharacterReceived(character: CharacterData) {
        if (networkManager.hasInternetConnection()) {
            characterData = character
            checkIfCharacterIsFavorite(character.isFav)
            getFirstEpisode(character)
        } else {
            view.showNoInternetConnection()
        }
    }

    private fun checkIfCharacterIsFavorite(isFav: Boolean) {
        if (isFav) {
            view.showCharacterAsFavorite()
        } else {
            view.showCharacterAsNonFavorite()
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

    fun onFavoriteClicked() {
        viewModelScope.launch(dispatcher) {
            characterData?.let { characterSelected ->
                setFavCharacterUseCase.setCharacterAsFav(!characterSelected.isFav, characterSelected.id)
                val favoriteCharacter = getFavCharactersUseCase.getFavCharacters()
                withContext(Dispatchers.Main) {
                    if (characterSelected.isFav) {
                        view.showCharacterAsNonFavorite()
                    } else {
                        view.showCharacterAsFavorite()
                    }
                    modifyCharacterStatus(favoriteCharacter, characterSelected)
                }
            }
        }
    }

    private fun modifyCharacterStatus(favoriteCharacter: List<CharacterData>, characterSelected: CharacterData) {
        val sameCharacter = favoriteCharacter.find { it.id == characterSelected.id }
        if (sameCharacter != null) {
            characterData = sameCharacter
        } else {
            characterSelected.isFav = false
        }
    }
}

interface CharacterDetailViewTranslator {
    fun showEpisode(characterAndEpisodeData: CharacterAndEpisodeData)
    fun showEpisodeError()
    fun showNoInternetConnection()
    fun showEpisodeExtraData(image: String, voteAverage: Double)
    fun showEpisodeExtraDataError()
    fun showCharacterAsFavorite()
    fun showCharacterAsNonFavorite()
}