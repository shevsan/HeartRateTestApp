package ua.oshevchuk.heartratetestapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ua.oshevchuk.heartratetestapp.ui.entities.HeartRateResultEntity
import ua.oshevchuk.heartratetestapp.ui.navigation.flowRoutes.CoreFlowRoutes
import ua.oshevchuk.heartratetestapp.ui.navigation.flowRoutes.OnBoardingFlowRoutes
import ua.oshevchuk.heartratetestapp.ui.screens.general.GeneralScreen
import ua.oshevchuk.heartratetestapp.ui.screens.history.HistoryScreen
import ua.oshevchuk.heartratetestapp.ui.screens.measurement.MeasurementScreen
import ua.oshevchuk.heartratetestapp.ui.screens.onboarding.advices.AdviceOnboardingScreen
import ua.oshevchuk.heartratetestapp.ui.screens.onboarding.notifications.NotificationsOnboardingScreen
import ua.oshevchuk.heartratetestapp.ui.screens.onboarding.tracker.TrackerOnboardingScreen
import ua.oshevchuk.heartratetestapp.ui.screens.results.MeasuringResultScreen
import ua.oshevchuk.heartratetestapp.ui.screens.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SplashScreen.route) {

        SplashScreen.get(navGraphBuilder = this, onRedirectToOnboardingScreens = {
            navController.navigate(TrackerOnboardingScreen.route) {
                popUpToInclusive(SplashScreen.route)
            }
        },
            onRedirectToGeneralScreen = {
                navController.navigate("${CoreFlowRoutes.MAIN.route}/true") {
                    popUpToInclusive("${CoreFlowRoutes.MAIN.route}/true")
                }
            })

        GeneralScreen.get(navGraphBuilder = this,
            onHistoryClicked = {
                navController.navigate(HistoryScreen.route)
            },
            onStartMeasure = {
                navController.navigate(MeasurementScreen.route)
            }
        )

        TrackerOnboardingScreen.get(navGraphBuilder = this, onNextClicked = {
            navController.navigate(AdviceOnboardingScreen.route) {
                popUpToInclusive(TrackerOnboardingScreen.route)
            }
        })
        AdviceOnboardingScreen.get(navGraphBuilder = this, onNextClicked = {
            navController.navigate(NotificationsOnboardingScreen.route) {
                popUpToInclusive(AdviceOnboardingScreen.route)
            }
        })

        NotificationsOnboardingScreen.get(
            navGraphBuilder = this,
            onNextClicked = {
                navController.navigate("${CoreFlowRoutes.MAIN.route}/false") {
                    popUpToInclusive(NotificationsOnboardingScreen.route)
                }
            }
        )

        HistoryScreen.get(
            navGraphBuilder = this,
            onBackClicked = {
                navController.navigate("${CoreFlowRoutes.MAIN.route}/true"){
                    popUpToInclusive("${CoreFlowRoutes.MAIN.route}/true")
                }
            }
        )

        MeasurementScreen.get(
            navGraphBuilder = this,
            onBackClicked = {
                navController.navigateUp()
            }, onHeartRateCalculated = {
                val timestamp = it.timestamp
                val heartRate = it.heartRate
                navController.navigate("${CoreFlowRoutes.RESULTS.route}/$timestamp/$heartRate")
            }
        )
        MeasuringResultScreen.get(navGraphBuilder = this, onHistoryClicked = {
            navController.navigate(HistoryScreen.route) {
                popUpToInclusive("${CoreFlowRoutes.MAIN.route}/true")
            }
        },
            onDoneClicked = {
                navController.navigate("${CoreFlowRoutes.MAIN.route}/true") {
                    popUpToInclusive("${CoreFlowRoutes.MAIN.route}/true")
                }
            })
    }

}

object SplashScreen : Screen(CoreFlowRoutes.SPLASH.route) {
    fun get(
        navGraphBuilder: NavGraphBuilder,
        onRedirectToGeneralScreen: () -> Unit,
        onRedirectToOnboardingScreens: () -> Unit
    ) {
        navGraphBuilder.composable(SplashScreen.route) {
            SplashScreen(
                modifier = Modifier
                    .fillMaxSize(),
                onRedirectToGeneralScreen = onRedirectToGeneralScreen,
                onRedirectToOnboardingScreens = onRedirectToOnboardingScreens
            )
        }
    }
}

object GeneralScreen : Screen("${CoreFlowRoutes.MAIN.route}/{isOpenedBefore}") {
    fun get(
        navGraphBuilder: NavGraphBuilder,
        onHistoryClicked: () -> Unit,
        onStartMeasure: () -> Unit
    ) {
        navGraphBuilder.composable(GeneralScreen.route, arguments = listOf(
            navArgument("isOpenedBefore") { type = NavType.BoolType }
        )) {
            val isOpenedBefore = it.arguments?.getBoolean("isOpenedBefore") ?: false
            GeneralScreen(
                modifier = Modifier.fillMaxSize(),
                onHistoryClicked = onHistoryClicked,
                onStartMeasure = onStartMeasure,
                isOpenedBefore = isOpenedBefore
            )
        }
    }
}

object HistoryScreen : Screen(CoreFlowRoutes.HISTORY.route) {
    fun get(navGraphBuilder: NavGraphBuilder, onBackClicked: () -> Unit) {
        navGraphBuilder.composable(HistoryScreen.route) {
            HistoryScreen(modifier = Modifier.fillMaxSize(), onBackClicked = onBackClicked)
        }
    }
}

object MeasurementScreen : Screen(CoreFlowRoutes.MEASUREMENT.route) {
    fun get(
        navGraphBuilder: NavGraphBuilder,
        onBackClicked: () -> Unit,
        onHeartRateCalculated: (HeartRateResultEntity) -> Unit
    ) {
        navGraphBuilder.composable(MeasurementScreen.route) {
            MeasurementScreen(
                modifier = Modifier.fillMaxSize(),
                onBackClicked = onBackClicked,
                onHeartRateCalculated = onHeartRateCalculated
            )
        }
    }
}


object TrackerOnboardingScreen : Screen(OnBoardingFlowRoutes.TRACKER_ONBOARDING.route) {
    fun get(navGraphBuilder: NavGraphBuilder, onNextClicked: () -> Unit) {
        navGraphBuilder.composable(TrackerOnboardingScreen.route) {
            TrackerOnboardingScreen(
                modifier = Modifier.fillMaxSize(),
                onNextClicked = onNextClicked
            )
        }
    }
}

object AdviceOnboardingScreen : Screen(OnBoardingFlowRoutes.ADVICE_ONBOARDING.route) {
    fun get(navGraphBuilder: NavGraphBuilder, onNextClicked: () -> Unit) {
        navGraphBuilder.composable(AdviceOnboardingScreen.route) {
            AdviceOnboardingScreen(modifier = Modifier.fillMaxSize(), onNextClicked = onNextClicked)
        }
    }
}

object NotificationsOnboardingScreen : Screen(OnBoardingFlowRoutes.NOTIFICATIONS_ONBOARDING.route) {
    fun get(navGraphBuilder: NavGraphBuilder, onNextClicked: () -> Unit) {
        navGraphBuilder.composable(NotificationsOnboardingScreen.route) {
            NotificationsOnboardingScreen(
                modifier = Modifier.fillMaxSize(),
                onNextClicked = onNextClicked
            )
        }
    }
}

object MeasuringResultScreen : Screen("${CoreFlowRoutes.RESULTS.route}/{timestamp}/{heartRate}") {
    fun get(
        navGraphBuilder: NavGraphBuilder,
        onDoneClicked: () -> Unit,
        onHistoryClicked: () -> Unit,
    ) {
        navGraphBuilder.composable(
            route = MeasuringResultScreen.route, arguments = listOf(
                navArgument("heartRate") { type = NavType.IntType },
                navArgument("timestamp") { type = NavType.LongType }
            )) {
            val heartRate = it.arguments?.getInt("heartRate") ?: 0
            val timestamp = it.arguments?.getLong("timestamp") ?: 0
            val measuringResult = HeartRateResultEntity(
                timestamp, heartRate
            )
            MeasuringResultScreen(
                modifier = Modifier.fillMaxSize(),
                measuringResults = measuringResult,
                onDoneClicked = onDoneClicked,
                onHistoryClicked = onHistoryClicked
            )
        }
    }
}

fun NavOptionsBuilder.popUpToInclusive(route: String) {
    popUpTo(route) {
        inclusive = true
    }
}