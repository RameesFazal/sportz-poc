package com.example.sportz.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.sportz.data.local.entity.SportDetailsEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class SportsDetailsDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_sports_db")
    lateinit var database: SportsDatabase
    private lateinit var dao: SportsDetailsDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.detailsDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertSportsItem() = runTest {
        val sportsDetailsEntity = SportDetailsEntity(
            id = 1,
            name = "Squash",
            sportsId = 220,
            description = "Sample Squash Description",
            sportsImage = ""
        )
        dao.insertSport(sportsDetailsEntity)
        val sportsItem = dao.getSportById(220)
        assertThat(sportsItem).isNotNull()
    }

    @Test
    fun selectItemBasedOnId() = runTest {
        val sportsDetailsEntityOne = SportDetailsEntity(
            id = 1,
            name = "Squash",
            sportsId = 220,
            description = "Sample Squash Description",
            sportsImage = ""
        )
        val sportsDetailsEntityTwo = SportDetailsEntity(
            id = 2,
            name = "Baseball",
            sportsId = 221,
            description = "Sample baseball Description",
            sportsImage = ""
        )
        dao.insertSport(sportsDetailsEntityOne)
        dao.insertSport(sportsDetailsEntityTwo)
        val sportsItem = dao.getSportById(221)
        assertThat(sportsItem?.id).isEqualTo(2)
    }

    @Test
    fun nullIfSportDoesNotExist() = runTest {
        val sportsDetailsEntityTwo = SportDetailsEntity(
            id = 2,
            name = "Baseball",
            sportsId = 221,
            description = "Sample baseball Description",
            sportsImage = ""
        )
        dao.insertSport(sportsDetailsEntityTwo)
        val sportsItem = dao.getSportById(220)
        assertThat(sportsItem).isNull()
    }
}