package com.example.sportz.presentation.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.sportz.data.repository.MockSportsDetailsRepository
import com.example.sportz.domain.model.SportsDetails
import com.example.sportz.domain.use_case.FetchSportsItemUseCase
import com.example.sportz.domain.use_case.SyncSportsDetailsUseCase
import com.example.sportz.getOrAwaitValueTest
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SportsDetailsViewModelTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockSportsDetailsRepository: MockSportsDetailsRepository
    private lateinit var sportsDetailsViewModel: SportsDetailsViewModel
    lateinit var useCaseFetchSportsDetails: FetchSportsItemUseCase
    private lateinit var syncSportsDetailsUseCase: SyncSportsDetailsUseCase
    private lateinit var sportDetails: SportsDetails
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockSportsDetailsRepository = MockSportsDetailsRepository()
        useCaseFetchSportsDetails = FetchSportsItemUseCase(mockSportsDetailsRepository)
        syncSportsDetailsUseCase = SyncSportsDetailsUseCase(mockSportsDetailsRepository)
        sportsDetailsViewModel = SportsDetailsViewModel(
            useCaseFetchSportsDetails, syncSportsDetailsUseCase,
            SavedStateHandle()
        )
        sportDetails = SportsDetails(
            id = 1,
            name = "Baseball",
            description = "A sample description",
            image = ""
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Successfully inserting to local storage`() = runBlocking {
        sportsDetailsViewModel.insertSportsDetail(sportDetails)
        val out = sportsDetailsViewModel.getSportsDetailsFromLocal(1).data
        Truth.assertThat(out).isEqualTo(sportDetails)
    }

    @Test
    fun `Skip inserting to local storage if the details is null`() = runBlocking {
        sportsDetailsViewModel.insertSportsDetail(null)
        val out = sportsDetailsViewModel.getSportsDetailsFromLocal(1).data
        Truth.assertThat(out).isNull()
    }

    @Test
    fun `New data is inserted to local storage when available`() = runBlocking {
        sportsDetailsViewModel.fetchSportsById(1)
        val out = sportsDetailsViewModel.getSportsDetailsFromLocal(1).data
        Truth.assertThat(out).isEqualTo(
            SportsDetails(
                id = 1,
                name = "Baseball",
                description = "A Sample Description",
                image = ""
            )
        )
    }


    @Test
    fun `List has data on success scenario`() = runBlocking {
        sportsDetailsViewModel.insertSportsDetail(sportDetails)
        sportsDetailsViewModel.fetchSportsById(1)
        val out = sportsDetailsViewModel.response.getOrAwaitValueTest()
        Truth.assertThat(out.data).isNotNull()
    }

    @Test
    fun `List has cached data on error scenario`() = runBlocking {
        mockSportsDetailsRepository = MockSportsDetailsRepository()
        mockSportsDetailsRepository.shouldReturnNetworkError = true
        useCaseFetchSportsDetails = FetchSportsItemUseCase(mockSportsDetailsRepository)
        syncSportsDetailsUseCase = SyncSportsDetailsUseCase(mockSportsDetailsRepository)
        sportsDetailsViewModel = SportsDetailsViewModel(useCaseFetchSportsDetails, syncSportsDetailsUseCase, SavedStateHandle())
        sportsDetailsViewModel.insertSportsDetail(sportDetails)
        sportsDetailsViewModel.fetchSportsById(1)
        val out = sportsDetailsViewModel.response.getOrAwaitValueTest()
        Truth.assertThat(out.data).isEqualTo(sportDetails)
    }
}