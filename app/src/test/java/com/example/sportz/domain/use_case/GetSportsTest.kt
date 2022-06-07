package com.example.sportz.domain.use_case

import com.example.sportz.common.Resource
import com.example.sportz.data.repository.MockSportsRepository
import com.example.sportz.domain.model.Sports
import com.example.sportz.domain.repository.SportsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetSportsTest {
    private lateinit var getSports: GetSports
    private lateinit var mockSportsRepository: MockSportsRepository
    private val sportsToInsert = mutableListOf<Sports>()

    @Mock
    private lateinit var sportsRepository: SportsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
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
    fun `Test sport list insertion to room db`() = runBlocking {
        val sports = mockSportsRepository.getSportsFromLocal()
        assertEquals(sports.size, 26)
    }

    @Test
    fun `Test db is cleared before refreshing`() = runBlocking {
        mockSportsRepository.clearAllSports()
        val sports = mockSportsRepository.getSportsFromLocal()
        assertEquals(sports.size, 0)
    }

    @Test
    fun `Test data refreshed on success scenario`() = runBlocking {
        sportsToInsert.add(Sports(id = 27, name = "Sample", icon = ""))
        getSports = GetSports(sportsRepository)
        Mockito.`when`(sportsRepository.getSports())
            .thenReturn(
                flow { emit(Resource.Success(sportsToInsert.toList())) })
        val output = getSports.invoke()
        assertEquals(output.first().data?.size, 27)
    }
}