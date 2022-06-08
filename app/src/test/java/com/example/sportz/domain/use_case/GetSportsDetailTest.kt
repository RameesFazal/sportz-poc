package com.example.sportz.domain.use_case

import com.example.sportz.data.repository.MockSportsDetailsRepository
import com.example.sportz.domain.model.SportsDetails
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class GetSportsDetailTest {

    private lateinit var getSportsDetail: GetSportsDetail
    private lateinit var mockSportsDetailRepository: MockSportsDetailsRepository
    private val sportsToInsert = mutableListOf<SportsDetails>()

    @Before
    fun setUp() {
        mockSportsDetailRepository = MockSportsDetailsRepository()
        getSportsDetail = GetSportsDetail(mockSportsDetailRepository)
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
    fun `Returns cached data on error scenario`() = runBlocking {
        mockSportsDetailRepository.shouldReturnNetworkError = true
        mockSportsDetailRepository.insertSportDetail(sportsToInsert[1])
        val output = getSportsDetail.invoke(1)
        assertEquals(output.first().data?.name, "b")
    }
}