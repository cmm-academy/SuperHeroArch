package com.mstudio.superheroarch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterDetailsViewModel(
    private val viewTranslator: CharacterDetailsViewTranslator,
    private val getCharactersDetailsUseCase: GetCharacterDetailsUseCase = GetCharacterDetailsUseCase(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    fun onCreate(character: Character?) {
        viewTranslator.showLoader()
        character?.let {
            viewModelScope.launch(dispatcher) {
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
    fun showCharacterInformation(fullCharacterInformation: FullCharacterEntity)
    fun showErrorMessage(error: String)
    fun showLoader()
    fun hideLoader()
}
