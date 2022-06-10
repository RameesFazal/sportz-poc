package com.example.sportz.presentation.listing

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.example.sportz.R
import com.example.sportz.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SportsListingFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun clickSportsItem_navigateToSportsDetails() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<SportsListingFragment>() {
            Navigation.setViewNavController(requireView(), navController)
            onView(withId(R.id.rv_sports_listing))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
            verify(navController).navigate(
                R.id.action_sportsListingFragment_to_sportsDetailsFragment
            )
        }
    }
}

