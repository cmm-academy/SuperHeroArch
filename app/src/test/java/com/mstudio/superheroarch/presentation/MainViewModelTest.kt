package com.mstudio.superheroarch.presentation

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.presentation.overview.CharactersFilters
import com.mstudio.superheroarch.presentation.overview.MainViewModel
import com.mstudio.superheroarch.presentation.overview.MainViewTranslator
import com.mstudio.superheroarch.repository.model.toCharacterData
import com.mstudio.superheroarch.usecase.GetAllCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var view: MainViewTranslator
    private lateinit var useCase: GetAllCharactersUseCase
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        useCase = mock()
        view = mock()
        viewModel = MainViewModel(view, useCase, testDispatcher)
    }

    @Test
    fun `given main screen, when retrieve all the characters, then show all the characters`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterEntity().toCharacterData())
        `when`(useCase.getAllCharacters()).thenReturn(expectedCharacters)
        viewModel.onStart()

        verify(view).showCharacters(expectedCharacters)
    }

    @Test()
    fun `given main screen, when retrieving all characters the call return empty list, then show empty error`() = runTest {
        `when`(useCase.getAllCharacters()).thenReturn(emptyList())
        viewModel.onStart()

        verify(view).showEmptyCharactersError()
    }

    @Test(expected = Exception::class)
    fun `given main screen, when retrieving all character the call fails, then show generic error`() = runTest {
        `when`(useCase.getAllCharacters()).thenThrow(Exception())
        viewModel.onStart()

        verify(view).showGenericError()
    }

    @Test
    fun `given main screen, when user clicks on dead filter, then show all dead characters only`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterEntity(status = CharactersFilters.DEAD.type).toCharacterData())
        `when`(useCase.getAllCharacters()).thenReturn(expectedCharacters)
        viewModel.onStart()
        viewModel.onFilterButtonClicked(CharactersFilters.DEAD)

        verify(view, times(2)).showCharacters(expectedCharacters.filter { it.status == CharactersFilters.DEAD.type })
    }

    @Test
    fun `given main screen, when user clicks on a character, then navigate to detail screen`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterEntity().toCharacterData())
        val characterSelected = expectedCharacters.first()
        `when`(useCase.getAllCharacters()).thenReturn(expectedCharacters)
        viewModel.onCharacterClicked(characterSelected)

        verify(view).goToDetailScreen(characterSelected)
    }

    @Test
    fun `given main screen, when user clicks on favorites button and has favorite characters, then show all the favorite characters`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterEntity(isFavorite = true).toCharacterData())
        `when`(useCase.getAllCharacters()).thenReturn(expectedCharacters)
        viewModel.onStart()

        viewModel.onFilterButtonClicked(CharactersFilters.FAVORITES)

        verify(view, times(2)).showCharacters(expectedCharacters.filter { it.isFavorite })
    }

    @Test
    fun `given main screen, when user clicks on favorites button and list is empty, then show empty list message`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterEntity().toCharacterData())
        `when`(useCase.getAllCharacters()).thenReturn(expectedCharacters)
        viewModel.onStart()
        viewModel.onFilterButtonClicked(CharactersFilters.FAVORITES)

        verify(view).showEmptyCharactersError()
    }
}