package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments.givenACharacterLocalEntity
import com.mstudio.superheroarch.localdatasource.CharactersDao
import com.mstudio.superheroarch.localdatasource.RickAndMortyDatabase
import com.mstudio.superheroarch.localdatasource.model.CharacterLocalEntity
import com.mstudio.superheroarch.remotedatasource.api.RickAndMortyApi
import com.mstudio.superheroarch.remotedatasource.model.RickAndMortyRemoteEntity
import com.mstudio.superheroarch.remotedatasource.model.toCharacterEntity
import com.mstudio.superheroarch.remotedatasource.model.toEpisodeEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
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
        `when`(rickAndMortyDatabase.characterDao()).thenReturn(givenDatabase(listOf(givenACharacterLocalEntity())))
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
    fun `given rickAndMorty repository, when set character as favorite, then update user as favorite in database`() = runTest {
        val result = rickAndMortyRepository.updateFavoriteCharacterStatus(true, 1)
        val localCharacters = rickAndMortyRepository.getCharacters()

        assertEquals(result, Unit)
        assertTrue(localCharacters.first().isFavorite)
    }

    private fun givenDatabase(characters: List<CharacterLocalEntity> = listOf(givenACharacterLocalEntity())): CharactersDao = object : CharactersDao {
        override suspend fun getCharacters(): List<CharacterLocalEntity> {
            return characters
        }

        override suspend fun insertCharacters(characters: List<CharacterLocalEntity>) = Unit

        override suspend fun updateFavoriteCharacter(isFavorite: Boolean, id: Int) {
            characters.find { id == it.id }?.isFavorite = isFavorite
        }

    }

}