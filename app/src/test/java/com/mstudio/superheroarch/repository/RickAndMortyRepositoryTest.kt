package com.mstudio.superheroarch.repository

import androidx.test.core.app.ApplicationProvider
import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.localdatasource.RickAndMortyDatabase
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.RickAndMortyRemoteEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class RickAndMortyRepositoryTest {

    @Mock
    private lateinit var rickAndMortyApiMock: RickAndMortyApi

    @Mock
    private lateinit var rickAndMortyDatabase: RickAndMortyDatabase

    @Mock
    private lateinit var rickAndMortyRepository: RickAndMortyRepository

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        `when`(rickAndMortyDatabase.characterDao()).thenReturn(mock())
        rickAndMortyRepository = RickAndMortyRepository(ApplicationProvider.getApplicationContext(), rickAndMortyApiMock)
    }

    @Test
    fun `given rickAndMorty repository, when retrieve all characters then return all characters`() = runTest {
        val characters = listOf(RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity())
        `when`(rickAndMortyApiMock.getCharacters()).thenReturn(Response.success(RickAndMortyRemoteEntity(characters)))
        val result = rickAndMortyRepository.getCharacters()
        assertEquals(characters, result)
    }

    @Test
    fun `given rickAndMorty repository, when retrieve episode data then return episode info`() = runTest {
        val episode = RickAndMortyRepositoryInstruments.givenAnEpisodeRemoteEntity()
        `when`(rickAndMortyApiMock.getSingleEpisode(1)).thenReturn(Response.success(episode))
        val result = rickAndMortyRepository.getSingleEpisode(1)
        assertEquals(episode, result)
    }

    @Test(expected = Exception::class)
    fun `given rickAndMorty repository, when get characters api call fails then return an exception`() = runTest {
        `when`(rickAndMortyApiMock.getCharacters()).thenThrow(Exception())
        rickAndMortyRepository.getCharacters()
    }

    @Test(expected = Exception::class)
    fun `given rickAndMorty repository, when get episode data api call fails then return an exception`() = runTest {
        `when`(rickAndMortyApiMock.getSingleEpisode(1)).thenThrow(Exception())
        rickAndMortyRepository.getSingleEpisode(1)
    }
}