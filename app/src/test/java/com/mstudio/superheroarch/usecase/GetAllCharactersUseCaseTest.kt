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

class GetAllCharactersUseCaseTest {

    private lateinit var repository: RickAndMortyRepository
    private lateinit var useCase: GetAllCharactersUseCase

    @Before
    fun before() {
        repository = mock()
        useCase = GetAllCharactersUseCase(repository)
    }

    @Test
    fun `given main screen, when screen is visited, then return all characters`() = runTest {
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterEntity())
        `when`(repository.getCharacters()).thenReturn(expectedCharacters)

        val result = useCase.getAllCharacters()
        assertEquals(expectedCharacters.map { it.toCharacterData() }, result)
    }
}