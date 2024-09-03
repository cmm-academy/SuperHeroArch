package com.mstudio.superheroarch.presentation

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.presentation.overview.MainViewModel
import com.mstudio.superheroarch.presentation.overview.MainViewTranslator
import com.mstudio.superheroarch.presentation.overview.StatusFilters
import com.mstudio.superheroarch.repository.model.toCharacterData
import com.mstudio.superheroarch.usecase.GetAllCharactersUseCase
import com.mstudio.superheroarch.usecase.GetFavCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private lateinit var view: MainViewTranslator
    private lateinit var useCase: GetAllCharactersUseCase
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var getFavCharactersUseCase: GetFavCharactersUseCase

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        useCase = mock()
        view = mock()
        getFavCharactersUseCase = mock()
        viewModel = MainViewModel(view, useCase, testDispatcher, getFavCharactersUseCase)
    }

    @Test
    fun `given main screen, when retrieve all the characters, then show all the characters`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterEntity().toCharacterData())
        `when`(useCase.getAllCharacters()).thenReturn(expectedCharacters)
        viewModel.onCreate()

        verify(view).showCharacters(expectedCharacters)
    }

    @Test()
    fun `given main screen, when retrieving all characters the call return empty list, then show empty error`() = runTest {
        `when`(useCase.getAllCharacters()).thenReturn(emptyList())
        viewModel.onCreate()

        verify(view).showEmptyCharactersError()
    }

    @Test(expected = Exception::class)
    fun `given main screen, when retrieving all character the call fails, then show generic error`() = runTest {
        `when`(useCase.getAllCharacters()).thenThrow(Exception())
        viewModel.onCreate()

        verify(view).showGenericError()
    }

    @Test
    fun `given main screen, when user clicks on dead filter, then show all dead characters only`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterEntity().toCharacterData())
        `when`(useCase.getAllCharacters()).thenReturn(expectedCharacters)
        viewModel.onFilterButtonClicked(StatusFilters.DEAD)

        verify(view).showCharacters(expectedCharacters.filter { it.status == StatusFilters.DEAD.status })
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
    fun `given main screen, when user clicks on fav button and has favorite characters, then show all the favorite characters`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterEntity(isFav = true).toCharacterData())

        `when`(getFavCharactersUseCase.getFavCharacters()).thenReturn(expectedCharacters)
        viewModel.onFavButtonClicked()

        verify(view).showCharacters(expectedCharacters.filter { it.isFav })
    }

    @Test
    fun `given main screen, when user clicks on fav button and list is empty, then show empty list message`() = runTest {
        `when`(getFavCharactersUseCase.getFavCharacters()).thenReturn(emptyList())
        viewModel.onFavButtonClicked()

        verify(view).showEmptyFavCharactersMessage()
    }
}