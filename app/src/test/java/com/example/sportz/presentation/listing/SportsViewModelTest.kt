package com.example.sportz.presentation.listing

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sportz.data.repository.MockSportsRepository
import com.example.sportz.domain.model.Sports
import com.example.sportz.domain.use_case.FetchSportsUseCase
import com.example.sportz.domain.use_case.SyncSportsListUseCase
import com.example.sportz.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class SportsViewModelTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockSportsRepository: MockSportsRepository
    private lateinit var sportsViewModel: SportsViewModel
    lateinit var useCaseFetchSports: FetchSportsUseCase
    private lateinit var syncSportsListUseCase: SyncSportsListUseCase
    private val sportsToInsert = mutableListOf<Sports>()
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        mockSportsRepository = MockSportsRepository()
        useCaseFetchSports = FetchSportsUseCase(mockSportsRepository)
        syncSportsListUseCase = SyncSportsListUseCase(mockSportsRepository)
        sportsViewModel = SportsViewModel(useCaseFetchSports, syncSportsListUseCase)
        ('a'..'z').forEachIndexed { index, c ->
            sportsToInsert.add(
                Sports(
                    name = c.toString(),
                    id = index,
                    icon = ""
                )
            )
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Successfully inserting to local storage`() = runBlocking {
        sportsViewModel.insertNewData(sportsToInsert.toList())
        val out = sportsViewModel.getSportsListFromLocal().data
        assertThat(out).contains(sportsToInsert[0])
    }

    @Test
    fun `Skip inserting to local storage if the list is null`() = runBlocking {
        sportsViewModel.clearAllData()
        sportsViewModel.insertNewData(null)
        val out = sportsViewModel.getSportsListFromLocal().data
        assertThat(out).isEmpty()
    }

    @Test
    fun `New data is inserted to local storage when available`() = runBlocking {
        sportsViewModel.fetchSportsList()
        val out = sportsViewModel.getSportsListFromLocal().data
        assertThat(out).contains(Sports(id = 27, name = "Boxing", icon = ""))
    }

    @Test
    fun `List has data on success scenario`() = runBlocking {
        sportsViewModel.fetchSportsList()
        val out = sportsViewModel.response.getOrAwaitValueTest()
        assertThat(out.data).isNotEmpty()
    }

    @Test
    fun `Function validate sports list having null name and icon`() {
        sportsToInsert.add(Sports(id = 55, name = null, icon = null))
        val outputList = sportsViewModel.validateList(sportsToInsert)
        assertThat(outputList).hasSize(26)
    }

    @Test
    fun `Skip inserting local storage if name is null`() = runBlocking {
        sportsToInsert.add(Sports(id = 55, name = null, icon = ""))
        sportsViewModel.insertNewData(sportsToInsert)
        val out = sportsViewModel.getSportsListFromLocal()
        assertThat(out.data).hasSize(26)
    }

    @Test
    fun `Skip inserting local storage if icon is null`() = runBlocking {
        sportsToInsert.add(Sports(id = 55, name = "BaseBall", icon = null))
        sportsViewModel.insertNewData(sportsToInsert)
        val out = sportsViewModel.getSportsListFromLocal()
        assertThat(out.data).hasSize(26)
    }

    @Test
    fun `List has cached data on error scenario`() = runBlocking {
        mockSportsRepository = MockSportsRepository()
        mockSportsRepository.shouldReturnNetworkError = true
        useCaseFetchSports = FetchSportsUseCase(mockSportsRepository)
        syncSportsListUseCase = SyncSportsListUseCase(mockSportsRepository)
        sportsViewModel = SportsViewModel(useCaseFetchSports, syncSportsListUseCase)
        sportsViewModel.insertNewData(sportsToInsert)
        sportsViewModel.fetchSportsList()
        val out = sportsViewModel.response.getOrAwaitValueTest()
        assertThat(out.data).hasSize(26)
    }
}