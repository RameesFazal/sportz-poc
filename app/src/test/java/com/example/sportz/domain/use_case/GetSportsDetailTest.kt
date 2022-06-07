package com.example.sportz.domain.use_case

import com.example.sportz.common.Resource
import com.example.sportz.data.repository.MockSportsDetailsRepository
import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.domain.repository.SportsDetailsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetSportsDetailTest {
    private lateinit var getSportsDetail: GetSportsDetail
    private lateinit var mockSportsDetailRepository: MockSportsDetailsRepository
    private val sportsToInsert = mutableListOf<SportsDetails>()

    @Mock
    private lateinit var sportsDetailsRepository: SportsDetailsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
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
        sportsToInsert.shuffle()
        runBlocking {
            sportsToInsert.forEach {
                mockSportsDetailRepository.insertSportDetail(it)
            }
        }
    }

    @Test
    fun `Test data refreshed on success scenario`() = runBlocking {
        sportsToInsert.add(SportsDetails(id = 27, name = "Sample", image = "", description = ""))
        getSportsDetail = GetSportsDetail(sportsDetailsRepository)
        Mockito.`when`(sportsDetailsRepository.getSportById(27))
            .thenReturn(
                flow { emit(Resource.Success(sportsToInsert.last())) })
        val output = getSportsDetail.invoke(27)
        Assert.assertEquals(output.first().data?.name, "Sample")
    }
}