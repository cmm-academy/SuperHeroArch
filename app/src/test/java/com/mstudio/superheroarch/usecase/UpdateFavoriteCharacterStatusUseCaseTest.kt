package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.repository.RickAndMortyRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.kotlin.verify

class UpdateFavoriteCharacterStatusUseCaseTest {

    private lateinit var repository: RickAndMortyRepository
    private lateinit var useCase: UpdateFavoriteCharacterStatusUseCase

    @Before
    fun before() {
        repository = mock()
        useCase = UpdateFavoriteCharacterStatusUseCase(repository)
    }

    @Test
    fun `given detail screen, when user select character as favorite, then character is mark as favorite`() = runTest {
        `when`(repository.updateFavoriteCharacterStatus(true, 1)).thenReturn(Unit)
        val result = useCase.updateCharacterFavoriteStatus(true, 1)
        assertEquals(Unit, result)
        verify(repository).updateFavoriteCharacterStatus(true, 1)
    }
}