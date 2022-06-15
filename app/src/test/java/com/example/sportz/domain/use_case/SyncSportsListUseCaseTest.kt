package com.example.sportz.domain.use_case

import com.example.sportz.data.repository.MockSportsRepository
import com.example.sportz.domain.model.Sports
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SyncSportsListUseCaseTest {
    private lateinit var syncSportsListUseCase: SyncSportsListUseCase
    private lateinit var mockSportsRepository: MockSportsRepository
    private val sportsToInsert = mutableListOf<Sports>()

    @Before
    fun setUp() {
        mockSportsRepository = MockSportsRepository()
        syncSportsListUseCase = SyncSportsListUseCase(mockSportsRepository)
        ('a'..'z').forEachIndexed { index, c ->
            sportsToInsert.add(
                Sports(
                    name = c.toString(),
                    id = index,
                    icon = c.toString()
                )
            )
        }
        sportsToInsert.shuffle()
    }

    @Test
    fun `UseCase inserting to local storage`() = runBlocking {
        syncSportsListUseCase.insertSports(sportsToInsert)
        assertEquals(syncSportsListUseCase.getSportsFromLocal().size, 26)
    }

    @Test
    fun `UseCase clearing sports list from local db`() = runBlocking {
        syncSportsListUseCase.insertSports(sportsToInsert)
        syncSportsListUseCase.clearAllSports()
        assertTrue(mockSportsRepository.getSportsFromLocal().isEmpty())
    }
}