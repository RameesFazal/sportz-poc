package com.example.sportz.domain.use_case

import com.example.sportz.data.repository.MockSportsRepository
import com.example.sportz.domain.model.Sports
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetSportsTest {
    private lateinit var getSports: GetSports
    private lateinit var mockSportsRepository: MockSportsRepository
    private val sportsToInsert = mutableListOf<Sports>()

    @Before
    fun setUp() {
        mockSportsRepository = MockSportsRepository()
        getSports = GetSports(mockSportsRepository)
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
    fun `Returns cached data on error scenario`() = runBlocking {
        mockSportsRepository.shouldReturnNetworkError = true
        val output = getSports.invoke()
        assertEquals(output.first().data?.size, 26)
    }

    @Test
    fun `Returns new data on success scenario`() = runBlocking {
        mockSportsRepository.shouldReturnNetworkError = false
        val output = getSports.invoke()
        assertEquals(output.first().data?.size, 1)
    }

    @Test
    fun `Refresh local db on new data`() = runBlocking {
        mockSportsRepository.shouldReturnNetworkError = false
        getSports.invoke()
        assertTrue(mockSportsRepository.getSportsFromLocal().isNotEmpty())
    }
}