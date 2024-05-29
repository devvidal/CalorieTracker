package com.dvidal.calorietracker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dvidal.calorietracker.ui.navigation.navigate
import com.dvidal.calorietracker.ui.theme.CalorieTrackerTheme
import com.dvidal.core.domain.preferences.Preferences
import com.dvidal.core.navigation.Route
import com.dvidal.onboarding_presentation.activitylevel.ActivityLevelScreen
import com.dvidal.onboarding_presentation.age.AgeScreen
import com.dvidal.onboarding_presentation.gender.GenderScreen
import com.dvidal.onboarding_presentation.goaltype.GoalTypeScreen
import com.dvidal.onboarding_presentation.height.HeightScreen
import com.dvidal.onboarding_presentation.nutrientgoal.NutrientGoalScreen
import com.dvidal.onboarding_presentation.weight.WeightScreen
import com.dvidal.onboarding_presentation.welcome.WelcomeScreen
import com.dvidal.tracker_presentation.search.SearchScreen
import com.dvidal.tracker_presentation.trackeroverview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()

        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if (shouldShowOnboarding) Route.WELCOME else Route.TRACKER_OVERVIEW
                    ) {
                        composable(Route.WELCOME){
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.AGE){
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.GENDER){
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.HEIGHT){
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.WEIGHT){
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.ACTIVITY_LEVEL){
                            ActivityLevelScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.GOAL_TYPE){
                            GoalTypeScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(Route.NUTRIENT_GOAL){
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = navController::navigate
                            )
                        }

                        composable(Route.TRACKER_OVERVIEW){
                            TrackerOverviewScreen(
                                onNavigate = navController::navigate
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
                        ){
                            val mealName = requireNotNull(it.arguments?.getString("mealName"))
                            val dayOfMonth = requireNotNull(it.arguments?.getInt("dayOfMonth"))
                            val month = requireNotNull(it.arguments?.getInt("month"))
                            val year = requireNotNull(it.arguments?.getInt("year"))

                            SearchScreen(
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
                    }.run { it }
                }
            }
        }
    }
}