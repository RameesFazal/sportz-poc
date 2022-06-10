package com.example.sportz.presentation.detail

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.filters.MediumTest
import com.example.sportz.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class SportsDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun popBackOnBackPress() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<SportsDetailsFragment> {
            Navigation.setViewNavController(requireView(), navController)
            pressBack()
            verify(navController).popBackStack()
        }
    }
}