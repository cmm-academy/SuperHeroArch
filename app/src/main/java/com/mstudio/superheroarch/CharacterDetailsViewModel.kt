package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailsViewModel(private val viewTranslator: CharacterDetailsViewTranslator) : ViewModel() {
    private val getCharactersDetailsUseCase = GetCharacterDetailsUseCase()

    fun onCreate(character: Character?) {
        viewTranslator.showLoader()
        character?.let {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val fullCharacterInformation = getCharactersDetailsUseCase.getCharacterDetails(it)
                    withContext(Dispatchers.Main) {
                        fullCharacterInformation?.let {
                            viewTranslator.showCharacterInformation(fullCharacterInformation)
                            viewTranslator.hideLoader()
                        } ?: run {
                            viewTranslator.showErrorMessage("Character details not found")
                        }
                    }
                } catch (e: Exception) {
                    viewTranslator.showErrorMessage(e.message ?: "Unknown error")
                    viewTranslator.hideLoader()
                }
            }
        }
    }
}

interface CharacterDetailsViewTranslator {
    fun showCharacterInformation(fullCharacterInformation: CharacterUIEntity)
    fun showErrorMessage(error: String)
    fun showLoader()
    fun hideLoader()
}
