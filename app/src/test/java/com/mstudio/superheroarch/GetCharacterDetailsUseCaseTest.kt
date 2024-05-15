package com.mstudio.superheroarch


import com.mstudio.superheroarch.datacreation.CharacterMockCreator
import com.mstudio.superheroarch.datacreation.EpisodeMockCreator
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetCharacterDetailsUseCaseTest {

    private lateinit var useCase: GetCharacterDetailsUseCase
    private lateinit var character: Character

    @Mock
    private lateinit var rickAndMortyRepository: RickAndMortyRepository

    @Mock
    private lateinit var tmdbRepository: TMDBRepository

    @Before
    fun setup() {
        useCase = GetCharacterDetailsUseCase(rickAndMortyRepository, tmdbRepository)
        character = CharacterMockCreator.createCharacterMock()
    }

    @Test
    fun `getCharacterDetails returns FullCharacterEntity when repositories return valid data`() = runTest {
        val episodeDetails = EpisodeMockCreator.createEpisodeMock()
        val tmdbEpisode = EpisodeMockCreator.createTMDBEpisodeDetailsMock()

        Mockito.`when`(rickAndMortyRepository.getEpisodeDetails(anyInt())).thenReturn(episodeDetails)
        Mockito.`when`(tmdbRepository.getEpisodeDetails(anyInt(), anyInt())).thenReturn(tmdbEpisode)

        val result = useCase.getCharacterDetails(character)

        Assert.assertNotNull(result)
    }

    @Test
    fun `getCharacterDetails returns null when repositories return invalid data`() = runTest {
        val episodeDetails = EpisodeMockCreator.createEpisodeMock()

        Mockito.`when`(rickAndMortyRepository.getEpisodeDetails(anyInt())).thenReturn(episodeDetails)
        Mockito.`when`(tmdbRepository.getEpisodeDetails(anyInt(), anyInt())).thenReturn(null)

        val result = useCase.getCharacterDetails(character)

        Assert.assertNull(result)
    }

}