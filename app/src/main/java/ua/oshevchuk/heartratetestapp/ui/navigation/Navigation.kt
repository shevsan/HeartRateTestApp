package ua.oshevchuk.heartratetestapp.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ua.oshevchuk.heartratetestapp.ui.navigation.flowRoutes.CoreFlowRoutes
import ua.oshevchuk.heartratetestapp.ui.navigation.flowRoutes.OnBoardingFlowRoutes
import ua.oshevchuk.heartratetestapp.ui.screens.general.GeneralScreen
import ua.oshevchuk.heartratetestapp.ui.screens.history.HistoryScreen
import ua.oshevchuk.heartratetestapp.ui.screens.measurement.MeasurementScreen
import ua.oshevchuk.heartratetestapp.ui.screens.onboarding.advices.AdviceOnboardingScreen
import ua.oshevchuk.heartratetestapp.ui.screens.onboarding.notifications.NotificationsOnboardingScreen
import ua.oshevchuk.heartratetestapp.ui.screens.onboarding.tracker.TrackerOnboardingScreen
import ua.oshevchuk.heartratetestapp.ui.screens.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SplashScreen.route) {
        SplashScreen.get(navGraphBuilder = this, onLoaded = {
            navController.navigate(TrackerOnboardingScreen.route) {
                popUpToInclusive(SplashScreen.route)
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
                navController.navigate(GeneralScreen.route) {
                    popUpToInclusive(NotificationsOnboardingScreen.route)
                }
            }
        )

        HistoryScreen.get(
            navGraphBuilder = this,
            onBackClicked = {
                navController.navigateUp()
            }
        )

        MeasurementScreen.get(
            navGraphBuilder = this,
            onBackClicked = {
                navController.navigateUp()
            }
        )
    }

}

object SplashScreen : Screen(CoreFlowRoutes.SPLASH.route) {
    fun get(navGraphBuilder: NavGraphBuilder, onLoaded: () -> Unit) {
        navGraphBuilder.composable(SplashScreen.route) {
            SplashScreen(
                modifier = Modifier
                    .fillMaxSize(),
                onLoaded = onLoaded
            )
        }
    }
}

object GeneralScreen : Screen(CoreFlowRoutes.MAIN.route) {
    fun get(
        navGraphBuilder: NavGraphBuilder,
        onHistoryClicked: () -> Unit,
        onStartMeasure: () -> Unit
    ) {
        navGraphBuilder.composable(GeneralScreen.route) {
            GeneralScreen(
                modifier = Modifier.fillMaxSize(),
                onHistoryClicked = onHistoryClicked,
                onStartMeasure = onStartMeasure
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
    fun get(navGraphBuilder: NavGraphBuilder, onBackClicked: () -> Unit) {
        navGraphBuilder.composable(MeasurementScreen.route) {
            MeasurementScreen(modifier = Modifier.fillMaxSize(), onBackClicked = onBackClicked)
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

fun NavOptionsBuilder.popUpToInclusive(route: String) {
    popUpTo(route) {
        inclusive = true
    }
}