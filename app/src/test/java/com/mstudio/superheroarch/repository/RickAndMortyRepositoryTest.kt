package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.localdatasource.RickAndMortyDatabase
import com.mstudio.superheroarch.localdatasource.model.toCharactersEntity
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.RickAndMortyRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.toCharacterEntity
import com.mstudio.superheroarch.remotedatasource.model.toEpisodeEntity
import com.mstudio.superheroarch.repository.model.CharacterEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class RickAndMortyRepositoryTest {

    private lateinit var rickAndMortyApiMock: RickAndMortyApi
    private lateinit var rickAndMortyDatabase: RickAndMortyDatabase
    private lateinit var rickAndMortyRepository: RickAndMortyRepository

    @Before
    fun before() {
        rickAndMortyDatabase = mock()
        rickAndMortyApiMock = mock()
        `when`(rickAndMortyDatabase.characterDao()).thenReturn(mock())
        rickAndMortyRepository = RickAndMortyRepository(rickAndMortyDatabase, rickAndMortyApiMock)
    }

    @Test
    fun `given rickAndMorty repository, when retrieve all characters then return all characters`() = runTest {
        val characters = listOf(RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity())
        `when`(rickAndMortyApiMock.getCharacters()).thenReturn(Response.success(RickAndMortyRemoteEntity(characters)))
        val result = rickAndMortyRepository.getCharacters()
        assertEquals(characters.map { it.toCharacterEntity() }, result)
    }

    @Test
    fun `given rickAndMorty repository, when retrieve episode data then return episode info`() = runTest {
        val episode = RickAndMortyRepositoryInstruments.givenAnEpisodeRemoteEntity()
        `when`(rickAndMortyApiMock.getSingleEpisode(1)).thenReturn(Response.success(episode))
        val result = rickAndMortyRepository.getSingleEpisode(1)
        assertEquals(episode.toEpisodeEntity(), result)
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

    @Test
    fun `given rickAndMorty repository, when retrieve all favorite characters, then return the list of favorite characters`() = runTest {
        val characters = listOf(RickAndMortyRepositoryInstruments.givenACharacterLocalEntity(isFav = true))
        `when`(rickAndMortyDatabase.characterDao().getFavouriteCharacters()).thenReturn(characters)
        val result = rickAndMortyRepository.getFavCharacters()
        assertEquals(characters.map { it.toCharactersEntity() }, result)
    }

    @Test
    fun `given rickAndMorty repository, when retrieve an empty list of favorite character, the return empty list`() = runTest {
        `when`(rickAndMortyDatabase.characterDao().getFavouriteCharacters()).thenReturn(emptyList())
        val result = rickAndMortyRepository.getFavCharacters()
        assertEquals(emptyList<CharacterEntity>(), result)
    }

    @Test
    fun `given rickAndMorty repository, when set character as favorite, then update user as favorite in database`() = runTest {
        `when`(rickAndMortyDatabase.characterDao().updateFavCharacter(true, 1)).thenReturn(Unit)
        val result = rickAndMortyRepository.setFavCharacter(true, 1)
        assertEquals(result, Unit)
    }
}