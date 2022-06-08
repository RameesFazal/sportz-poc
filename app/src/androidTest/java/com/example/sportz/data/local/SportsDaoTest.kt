package com.example.sportz.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.sportz.data.local.entity.SportsEntity
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
class SportsDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_sports_db")
    lateinit var database: SportsDatabase
    private lateinit var dao: SportsDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = database.dao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertSportsList() = runTest {
        val sportsEntity = SportsEntity(
            id = 1,
            name = "cycling",
            sportsId = 1,
            icon = "https://sports-api-production.s3.amazonaws.com/uploads/sport/icon/224/224.svg"
        )
        val sportsList = mutableListOf<SportsEntity>()
        sportsList.add(sportsEntity)
        dao.insertSports(sportsList.toList())
        val getItems = dao.getAllSports()
        assertThat(getItems).contains(sportsEntity)
    }

    @Test
    fun clearSportsList() = runTest {
        val sportsEntity = SportsEntity(
            id = 1,
            name = "cycling",
            sportsId = 1,
            icon = "https://sports-api-production.s3.amazonaws.com/uploads/sport/icon/224/224.svg"
        )
        val sportsList = mutableListOf<SportsEntity>()
        sportsList.add(sportsEntity)
        dao.insertSports(sportsList.toList())
        dao.clearAllEntities()
        val getItems = dao.getAllSports()
        assertThat(getItems).doesNotContain(sportsEntity)
    }
}