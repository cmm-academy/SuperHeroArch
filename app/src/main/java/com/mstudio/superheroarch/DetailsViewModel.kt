package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsViewModel(private val view: DetailsViewTranslator) : ViewModel() {

    private val apiRick: ApiRick = ApiService.retrofit.create(ApiRick::class.java)

    fun fetchCharacterDetails(character: Character) {
        view.displayCharacterDetails(character)
        fetchFirstEpisodeDetails(character.episode.firstOrNull())
    }

    private fun fetchFirstEpisodeDetails(episodeUrl: String?) {
        if (episodeUrl == null) return

        CoroutineScope(Dispatchers.IO).launch {
            val response = apiRick.getEpisode(episodeUrl)
            if (response.isSuccessful) {
                val episode = response.body()
                withContext(Dispatchers.Main) {
                    episode?.let {
                        view.displayFirstEpisodeDetails(it)
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    view.showError("Failed to fetch episode details")
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