package com.example.sportz.domain.use_case

import com.example.sportz.data.repository.MockSportsDetailsRepository
import com.example.sportz.domain.model.SportsDetails
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class FetchSportsUseCaseDetailTest {

    private lateinit var fetchSportsItemUseCase: FetchSportsItemUseCase
    private lateinit var mockSportsDetailRepository: MockSportsDetailsRepository
    private val sportsToInsert = mutableListOf<SportsDetails>()

    @Before
    fun setUp() {
        mockSportsDetailRepository = MockSportsDetailsRepository()
        fetchSportsItemUseCase = FetchSportsItemUseCase(mockSportsDetailRepository)
        ('a'..'z').forEachIndexed { index, c ->
            sportsToInsert.add(
                SportsDetails(
                    name = c.toString(),
                    id = index,
                    description = c.toString(),
                    image = ""
                )
            )
        }
    }

    @Test
    fun `Returns error when data not available`() = runBlocking {
        mockSportsDetailRepository.shouldReturnNetworkError = true
        val output = fetchSportsItemUseCase.invoke(1)
        assertEquals(output.first().message, "Oops! Something went wrong")
    }

    @Test
    fun `Returns new data on success scenario`() = runBlocking {
        mockSportsDetailRepository.shouldReturnNetworkError = false
        val output = fetchSportsItemUseCase.invoke(1)
        assertEquals(output.first().data?.name, "Baseball")
    }
}