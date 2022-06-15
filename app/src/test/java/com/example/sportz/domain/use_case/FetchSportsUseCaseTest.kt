package com.example.sportz.domain.use_case

import com.example.sportz.data.repository.MockSportsRepository
import com.example.sportz.domain.model.Sports
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FetchSportsUseCaseTest {
    private lateinit var fetchSportsUseCase: FetchSportsUseCase
    private lateinit var mockSportsRepository: MockSportsRepository
    private val sportsToInsert = mutableListOf<Sports>()

    @Before
    fun setUp() {
        mockSportsRepository = MockSportsRepository()
        fetchSportsUseCase = FetchSportsUseCase(mockSportsRepository)
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
        runBlocking {
            mockSportsRepository.insertSports(sportsToInsert)
        }
    }

    @Test
    fun `Returns error when data not available`() = runBlocking {
        mockSportsRepository.shouldReturnNetworkError = true
        val output = fetchSportsUseCase.invoke()
        assertEquals(output.first().message, "Oops! Something went wrong")
    }

    @Test
    fun `Returns new data on success scenario`() = runBlocking {
        mockSportsRepository.shouldReturnNetworkError = false
        val output = fetchSportsUseCase.invoke()
        assertEquals(output.first().data?.size, 1)
    }
}