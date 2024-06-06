package com.mstudio.superheroarch

import androidx.test.core.app.ApplicationProvider
import com.mstudio.superheroarch.datacreation.CharacterMockCreator
import com.mstudio.superheroarch.datacreation.EpisodeMockCreator
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class RickAndMortyRepositoryTest {

    @Mock
    private lateinit var mockApi: RickAndMortyApi

    @Mock
    private lateinit var database: RickAndMortyDatabase
    private lateinit var repository: RickAndMortyRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        `when`(database.characterDao()).thenReturn(mock())
        repository = RickAndMortyRepository(mockApi, ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testGetCharacters() = runTest {
        val mockCharacters = listOf(CharacterMockCreator.createCharacterMock())
        `when`(mockApi.getCharacters()).thenReturn(Response.success(ApiResponseWrapper(mockCharacters)))
        val result = repository.getCharacters(true)
        assertEquals(mockCharacters, result)
        verify(mockApi).getCharacters()
    }

    @Test
    fun testGetEpisodeDetails() = runTest {
        val mockEpisode = EpisodeMockCreator.createEpisodeMock()
        `when`(mockApi.getEpisodeDetails(anyInt())).thenReturn(Response.success(mockEpisode))
        val result = repository.getEpisodeDetails(1)
        assertEquals(mockEpisode, result)
    }
}