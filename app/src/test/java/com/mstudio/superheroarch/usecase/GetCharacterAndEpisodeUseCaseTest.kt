package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toTheMovieDbEntity
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.TheMovieDbRepository
import com.mstudio.superheroarch.repository.model.toCharacterData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCharacterAndEpisodeUseCaseTest {

    private lateinit var usecase: GetCharacterAndEpisodeUseCase
    private var characterData = RickAndMortyRepositoryInstruments.givenACharacterEntity().toCharacterData()
    private lateinit var rickAndMortyRepository: RickAndMortyRepository
    private lateinit var theMovieDbRepository: TheMovieDbRepository

    @Before
    fun before() {
        rickAndMortyRepository = mock()
        theMovieDbRepository = mock()
        usecase = GetCharacterAndEpisodeUseCase(rickAndMortyRepository, theMovieDbRepository)
    }

    @Test
    fun `given character detail, when screen is visited, then return the complete data`() = runTest {
        val expectedResult = RickAndMortyRepositoryInstruments.givenCharacterCompleteDetailData()
        val episodeDetails = RickAndMortyRepositoryInstruments.givenAnEpisodeEntity()
        val episodeExtraData = RickAndMortyRepositoryInstruments.givenAnEpisodeExtraDataEntity()

        `when`(rickAndMortyRepository.getSingleEpisode(1)).thenReturn(episodeDetails)
        `when`(theMovieDbRepository.getRickAndMortyEpisodeDetails(1, 1)).thenReturn(episodeExtraData)

        val result = usecase.getCharacterAndEpisode(characterData)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `given character detail, when episode details fails, the return null`() = runTest {
        `when`(rickAndMortyRepository.getSingleEpisode(1)).thenReturn(null)

        val result = usecase.getCharacterAndEpisode(characterData)
        assertNull(result)
    }

    @Test
    fun `given character detail, when episode extra data api call fails, then return everything but episode image and vote average`() = runTest {
        val expectedResult = RickAndMortyRepositoryInstruments.givenCharacterCompleteDetailData(null, null)
        val episodeDetails = RickAndMortyRepositoryInstruments.givenAnEpisodeEntity()

        `when`(rickAndMortyRepository.getSingleEpisode(1)).thenReturn(episodeDetails)
        `when`(theMovieDbRepository.getRickAndMortyEpisodeDetails(1, 1)).thenReturn(null)

        val result = usecase.getCharacterAndEpisode(characterData)
        assertEquals(expectedResult, result)
    }
}