package com.example.sportz.domain.use_case

import com.example.sportz.data.repository.MockSportsDetailsRepository
import com.example.sportz.domain.model.SportsDetails
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SyncSportsDetailsUseCaseTest {

    private lateinit var syncSportsDetailsUseCase: SyncSportsDetailsUseCase
    private lateinit var mockSportsDetailRepository: MockSportsDetailsRepository
    private val sportsToInsert = mutableListOf<SportsDetails>()

    @Before
    fun setUp() {
        mockSportsDetailRepository = MockSportsDetailsRepository()
        syncSportsDetailsUseCase = SyncSportsDetailsUseCase(mockSportsDetailRepository)
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
    fun `UseCase inserting to local storage`() = runBlocking {
        syncSportsDetailsUseCase.insertSportsDetail(
            SportsDetails(
                id = 1,
                name = "BaseBall",
                description = "A Sample description",
                image = ""
            )
        )
        Assert.assertEquals(syncSportsDetailsUseCase.getSportsFromLocal(1)?.name, "BaseBall")
    }
}