package com.dvidal.calorietracker

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dvidal.calorietracker.navigation.Route
import com.dvidal.calorietracker.repository.TrackerRepositoryFake
import com.dvidal.calorietracker.ui.MainActivity
import com.dvidal.calorietracker.ui.theme.CalorieTrackerTheme
import com.dvidal.core.domain.model.ActivityLevel
import com.dvidal.core.domain.model.Gender
import com.dvidal.core.domain.model.GoalType
import com.dvidal.core.domain.model.UserInfo
import com.dvidal.core.domain.preferences.Preferences
import com.dvidal.core.domain.usecase.FilterOutDigits
import com.dvidal.tracker_domain.model.TrackableFood
import com.dvidal.tracker_domain.usecase.CalculateMealNutrients
import com.dvidal.tracker_domain.usecase.DeleteTrackedFood
import com.dvidal.tracker_domain.usecase.GetFoodsForDate
import com.dvidal.tracker_domain.usecase.SearchFood
import com.dvidal.tracker_domain.usecase.TrackFood
import com.dvidal.tracker_domain.usecase.TrackerUseCases
import com.dvidal.tracker_presentation.search.SearchScreen
import com.dvidal.tracker_presentation.search.SearchViewModel
import com.dvidal.tracker_presentation.trackeroverview.TrackerOverviewScreen
import com.dvidal.tracker_presentation.trackeroverview.TrackerOverviewViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.roundToInt

@HiltAndroidTest
class TrackerOverviewE2E {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repostoryFake: TrackerRepositoryFake
    private lateinit var trackerUseCases: TrackerUseCases
    private lateinit var preferences: Preferences
    private lateinit var trackerOverviewViewModel: TrackerOverviewViewModel
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        preferences = mockk(relaxed = true)
        every { preferences.loadUserInfo() } returns UserInfo(
            gender = Gender.Male,
            age = 20,
            weight = 80f,
            height = 180,
            activityLevel = ActivityLevel.Medium,
            goalType = GoalType.KeepWeight,
            carbRatio = 0.4f,
            proteinRatio = 0.3f,
            fatRatio = 0.3f
        )

        repostoryFake = TrackerRepositoryFake()
        trackerUseCases = TrackerUseCases(
            trackFood = TrackFood(repostoryFake),
            searchFood = SearchFood(repostoryFake),
            getFoodsForDate = GetFoodsForDate(repostoryFake),
            deleteTrackedFood = DeleteTrackedFood(repostoryFake),
            calculateMealNutrients = CalculateMealNutrients(preferences)
        )

        trackerOverviewViewModel = TrackerOverviewViewModel(
            preferences = preferences,
            trackerUseCases = trackerUseCases
        )

        searchViewModel = SearchViewModel(
            trackerUseCases = trackerUseCases,
            filterOutDigits = FilterOutDigits()
        )

        composeRule.activity.setContent {
            CalorieTrackerTheme {
                val scaffoldState = rememberScaffoldState()
                navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.TRACKER_OVERVIEW
                    ) {
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                viewModel = trackerOverviewViewModel,
                                onNavigateToSearch = { mealName, dayOfMonth, month, year ->
                                    navController.navigate(
                                        Route.SEARCH
                                                + "/${mealName}"
                                                + "/${dayOfMonth}"
                                                + "/${month}"
                                                + "/${year}"
                                    )
                                }
                            )
                        }
                        composable(
                            route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val mealName = requireNotNull(it.arguments?.getString("mealName"))
                            val dayOfMonth = requireNotNull(it.arguments?.getInt("dayOfMonth"))
                            val month = requireNotNull(it.arguments?.getInt("month"))
                            val year = requireNotNull(it.arguments?.getInt("year"))

                            SearchScreen(
                                viewModel = searchViewModel,
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }

        }
    }

    @Test
    fun addBreakfast_appearsUnderBreakfast_nutrientsProperlyCalculated() {
        val trackableFood = TrackableFood(
            name = "banana",
            imageUrl = null,
            caloriesPer100g = 150,
            carbsPer100g = 50,
            proteinPer100g = 5,
            fatPer100g = 1
        )
        repostoryFake.searchResults = listOf(trackableFood)

        val addedAmount = 150
        val expectedCalories = (1.5f * trackableFood.caloriesPer100g).roundToInt()
        val expectedCarbs = (1.5f * trackableFood.carbsPer100g).roundToInt()
        val expectedProtein = (1.5f * trackableFood.proteinPer100g).roundToInt()
        val expectedFat = (1.5f * trackableFood.fatPer100g).roundToInt()

        composeRule
            .onNodeWithText("Add Breakfast")
            .assertDoesNotExist()
        composeRule
            .onNodeWithContentDescription("Breakfast")
            .performClick()
        composeRule
            .onNodeWithText("Add Breakfast")
            .assertIsDisplayed()
        composeRule
            .onNodeWithText("Add Breakfast")
            .performClick()

        assertThat(navController
            .currentDestination
            ?.route
            ?.startsWith(Route.SEARCH)
        ).isTrue()

        composeRule
            .onNodeWithTag("search_text_field")
            .performTextInput("banana")
        composeRule
            .onNodeWithContentDescription("Search...")
            .performClick()

        composeRule
            .onNodeWithText("Carbs")
            .performClick()
        composeRule
            .onNodeWithContentDescription("Amount")
            .performTextInput(addedAmount.toStr())
        composeRule
            .onNodeWithContentDescription("Track")
            .performClick()

        assertThat(navController
            .currentDestination
            ?.route
            ?.startsWith(Route.TRACKER_OVERVIEW)
        ).isTrue()

        composeRule
            .onAllNodesWithText(expectedCarbs.toStr())
            .onFirst()
            .assertIsDisplayed()
        composeRule
            .onAllNodesWithText(expectedProtein.toStr())
            .onFirst()
            .assertIsDisplayed()
        composeRule
            .onAllNodesWithText(expectedFat.toStr())
            .onFirst()
            .assertIsDisplayed()
        composeRule
            .onAllNodesWithText(expectedCalories.toStr())
            .onFirst()
            .assertIsDisplayed()
    }
}