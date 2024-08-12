package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.RickAndMortyRepositoryInstruments
import com.mstudio.superheroarch.remotedatasource.model.toCharacterData
import com.mstudio.superheroarch.repository.RickAndMortyRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
        val expectedCharacters = listOf(RickAndMortyRepositoryInstruments.givenACharacterRemoteEntity())
        `when`(repository.getCharacters()).thenReturn(expectedCharacters)

        val result = useCase.getAllCharacters()
        assertEquals(expectedCharacters.map { it.toCharacterData() }, result)
    }

    @Test
    fun `given main screen, when get all characters api call fails, then return null`() = runTest {
        `when`(repository.getCharacters()).thenReturn(null)

        val result = useCase.getAllCharacters()
        assertNull(result)
    }
}