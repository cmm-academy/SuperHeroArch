package com.mstudio.superheroarch.presentation

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.presentation.detail.CharacterDetailViewModel
import com.mstudio.superheroarch.presentation.detail.CharacterDetailViewTranslator
import com.mstudio.superheroarch.presentation.network.NetworkManagerImpl
import com.mstudio.superheroarch.repository.model.toCharacterData
import com.mstudio.superheroarch.usecase.GetCharacterAndEpisodeUseCase
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
class CharacterDetailViewModelTest {

    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var view: CharacterDetailViewTranslator
    private lateinit var useCase: GetCharacterAndEpisodeUseCase
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var networkManager: NetworkManagerImpl

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
        networkManager = mock()
        useCase = mock()
        view = mock()
        `when`(networkManager.hasInternetConnection()).thenReturn(true)

        viewModel = CharacterDetailViewModel(view, useCase, testDispatcher, networkManager)
    }

    @Test
    fun `given a character detail screen, when screen is visited, show all the character info`() = runTest {
        val characterData = RickAndMortyRepositoryInstruments.givenACharacterEntity().toCharacterData()

        `when`(
            useCase.getCharacterAndEpisode(
                characterData
            )
        ).thenReturn(RickAndMortyRepositoryInstruments.givenCharacterCompleteDetailData())
        viewModel.onCharacterReceived(characterData)

        verify(view).showEpisode(RickAndMortyRepositoryInstruments.givenCharacterCompleteDetailData())
        verify(view).showEpisodeExtraData("imageUrl", 7.0)
    }

    @Test
    fun `given a character detail screen, when screen is visited and episode extra data call fails, then the episode extra data error is shown`() = runTest {
        val characterData = RickAndMortyRepositoryInstruments.givenACharacterEntity().toCharacterData()
        `when`(
            useCase.getCharacterAndEpisode(
                characterData
            )
        ).thenReturn(RickAndMortyRepositoryInstruments.givenCharacterCompleteDetailData(null, null))
        viewModel.onCharacterReceived(characterData)
        verify(view).showEpisode(RickAndMortyRepositoryInstruments.givenCharacterCompleteDetailData(null, null))
        verify(view).showEpisodeExtraDataError()
    }

    @Test
    fun `given a character detail screen, when screen is visited and episode call fails, then the episode generic error is shown`() = runTest {
        val characterData = RickAndMortyRepositoryInstruments.givenACharacterEntity().toCharacterData()
        `when`(
            useCase.getCharacterAndEpisode(
                characterData
            )
        ).thenReturn(null)
        viewModel.onCharacterReceived(characterData)
        verify(view).showEpisodeError()
    }

    @Test
    fun `given a character detail screen, when screen is visited and there is no internet connection, then the no internet connection error is shown`() = runTest {
        `when`(networkManager.hasInternetConnection()).thenReturn(false)
        viewModel.onCharacterReceived(RickAndMortyRepositoryInstruments.givenACharacterEntity().toCharacterData())
        verify(view).showNoInternetConnection()
    }
}