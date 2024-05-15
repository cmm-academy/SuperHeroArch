package com.mstudio.superheroarch

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class CharacterDetailsViewModelTest {

    private lateinit var viewModel: CharacterDetailsViewModel
    private lateinit var viewTranslator: CharacterDetailsViewTranslator
    private lateinit var useCase: GetCharacterDetailsUseCase
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        useCase = mock()
        viewTranslator = mock()
        viewModel = CharacterDetailsViewModel(viewTranslator, useCase, testDispatcher)
    }

    @Test
    fun `onCreate with null character calls showErrorMessage`() = runTest() {

        val character: Character = mock()
        `when`(useCase.getCharacterDetails(character)).thenReturn(null)
        viewModel.onCreate(character)

        verify(viewTranslator).showLoader()
        verify(viewTranslator).showErrorMessage("Character details not found")
    }

    @Test
    fun `onCreate with non-null character calls showLoader`() = runTest() {
        val character: Character = mock()

        viewModel.onCreate(character)

        verify(viewTranslator).showLoader()
    }

    @Test

    fun `onCreate with character when useCase returns character calls showCharacter`() = runTest() {
        val character: Character = mock()
        val fullCharacterInformation: FullCharacterEntity = mock()
        `when`(useCase.getCharacterDetails(character)).thenReturn(fullCharacterInformation)
        viewModel.onCreate(character)

        verify(viewTranslator).showLoader()
        verify(viewTranslator).showCharacterInformation(fullCharacterInformation)
    }
}