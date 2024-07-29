package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val view: DetailsViewTranslator, private val repository: RickAndMortyRepository) : ViewModel() {

    fun fetchCharacterDetails(character: Character) {
        view.displayCharacterDetails(character)
        fetchFirstEpisodeDetails(character.episode.firstOrNull())
    }

    private fun fetchFirstEpisodeDetails(episodeUrl: String?) {
        if (episodeUrl == null) return

        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.fetchEpisodeDetails(episodeUrl)
            withContext(Dispatchers.Main) {
                result.onSuccess { episode ->
                    view.displayFirstEpisodeDetails(episode)
                }.onFailure {
                    view.showError("Error fetching episode details")
                }
            }
        }
    }
}

interface DetailsViewTranslator {
    fun displayCharacterDetails(character: Character)
    fun displayFirstEpisodeDetails(episode: Episode)
    fun showError(message: String)
}