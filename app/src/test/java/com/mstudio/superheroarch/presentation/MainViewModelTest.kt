package com.mstudio.superheroarch.presentation

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.presentation.overview.MainViewModel
import com.mstudio.superheroarch.presentation.overview.MainViewTranslator
import com.mstudio.superheroarch.presentation.overview.StatusFilters
import com.mstudio.superheroarch.remotedatasource.model.toCharacterData
import com.mstudio.superheroarch.repository.RickAndMortyRepository
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
    private lateinit var repository: RickAndMortyRepository
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        view = mock()
        repository = mock()
        viewModel = MainViewModel(view, repository, testDispatcher)
    }

    @Test
    fun `given main screen, when retrieve all the characters, then show all the characters`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity())
        `when`(repository.getCharacters()).thenReturn(expectedCharacters)
        viewModel.onCreate()

        verify(view).showCharacters(expectedCharacters.map { it.toCharacterData() })
    }

    @Test
    fun `given main screen, when retrieving all characters the call return empty list, then show empty error`() = runTest {
        `when`(repository.getCharacters()).thenReturn(emptyList())
        viewModel.onCreate()

        verify(view).showEmptyCharactersError()
    }

    @Test(expected = Exception::class)
    fun `given main screen, when retrieving all character the call fails, then show generic error`() = runTest {
        `when`(repository.getCharacters()).thenThrow(Exception())
        viewModel.onCreate()

        verify(view).showGenericError()
    }

    @Test
    fun `given main screen, when user clicks on dead filter, then show all dead characters only`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity())
        `when`(repository.getCharacters()).thenReturn(expectedCharacters)
        viewModel.onFilterButtonClicked(StatusFilters.DEAD)

        verify(view).showCharacters(expectedCharacters.filter { it.status == StatusFilters.DEAD.status }.map { it.toCharacterData() })
    }

    @Test
    fun `given main screen, when user clicks on a character, then navigate to detail screen`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity())
        val characterSelected = expectedCharacters.first().toCharacterData()
        `when`(repository.getCharacters()).thenReturn(expectedCharacters)
        viewModel.onCharacterClicked(characterSelected)

        verify(view).goToDetailScreen(characterSelected)
    }
}