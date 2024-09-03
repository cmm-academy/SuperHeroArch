package com.mstudio.superheroarch.usecase

import com.mstudio.superheroarch.repository.RickAndMortyRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class SetFavCharacterUseCaseTest {

    private lateinit var repository: RickAndMortyRepository
    private lateinit var useCase: SetFavCharacterUseCase

    @Before
    fun before() {
        repository = mock()
        useCase = SetFavCharacterUseCase(repository)
    }

    @Test
    fun `given detail screen, when user select character as favorite, then character is mark as favorite`() = runTest {
        `when`(repository.setFavCharacter(true, 1)).thenReturn(Unit)
        val result = useCase.setCharacterAsFav(true, 1)
        assertEquals(Unit, result)
    }
}