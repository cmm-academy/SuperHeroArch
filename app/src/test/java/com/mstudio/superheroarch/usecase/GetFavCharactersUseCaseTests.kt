package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import com.mstudio.superheroarch.repository.model.toCharacterData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetFavCharactersUseCaseTests {

    private lateinit var repository: RickAndMortyRepository
    private lateinit var useCase: GetFavCharactersUseCase

    @Before
    fun before() {
        repository = mock()
        useCase = GetFavCharactersUseCase(repository)
    }

    @Test
    fun `given main screen, when user has favorite characters, the return all the favorite characters`() = runTest {
        val expectedFavCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterEntity(isFav = true))
        `when`(repository.getFavCharacters()).thenReturn(expectedFavCharacters)
        val result = useCase.getFavCharacters()
        assertEquals(expectedFavCharacters.map { it.toCharacterData() }, result)
    }
}