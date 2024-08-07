package com.mstudio.superheroarch.repository

import com.mstudio.superheroarch.BuildConfig
import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.api.TheMovieDbApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Response

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
class TheMovieDbRepositoryTest {

    @Mock
    private lateinit var theMovieDbApiMock: TheMovieDbApi

    @Mock
    private lateinit var theMovieDbRepository: TheMovieDbRepository

    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        theMovieDbRepository = TheMovieDbRepository(theMovieDbApiMock)
    }

    @Test
    fun `given themoviedb repository, when retrieve episode extra data, then return extra data`() = runTest {
        val expectedResult = RickAndMortyRepositoryInstruments.givenAnEpisodeExtraData()
        Mockito.`when`(theMovieDbApiMock.getRickAndMortyEpisodeDetails("Bearer ${BuildConfig.TMDB_API_KEY}", 1, 1)).thenReturn(Response.success(expectedResult))
        val result = theMovieDbRepository.getRickAndMortyEpisodeDetails(1, 1)
        assertEquals(expectedResult.copy("https://image.tmdb.org/t/p/w500image"), result)
    }

    @Test(expected = Exception::class)
    fun `given themoviedb repository, when the api call fails, return an exception`() = runTest {
        Mockito.`when`(theMovieDbApiMock.getRickAndMortyEpisodeDetails("Bearer ${BuildConfig.TMDB_API_KEY}", 1, 1)).thenThrow(Exception())
        theMovieDbRepository.getRickAndMortyEpisodeDetails(1, 1)
    }
}