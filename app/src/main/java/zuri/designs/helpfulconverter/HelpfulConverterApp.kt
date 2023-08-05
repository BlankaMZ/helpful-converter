package zuri.designs.helpfulconverter

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import zuri.designs.helpfulconverter.ui.NewConverterScreen
import zuri.designs.helpfulconverter.ui.StartScreen
import zuri.designs.helpfulconverter.ui.UseConverterScreen

@Composable
fun HelpfulConverterApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold() { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ConverterScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(ConverterScreen.Start.name) {
                StartScreen(
                    onNewConverterButtonClicked = { navController.navigate(ConverterScreen.NewConverter.name) },
                    onExistingConverterClicked = { converterId ->
                        navController.navigate("${ConverterScreen.UseConverter.name}?$CONVERTER_ID=$converterId")
                    }
                )
            }
            composable(ConverterScreen.NewConverter.name) {
                NewConverterScreen(
                    popUpScreen = { navController.popBackStack() }
                )
            }
            composable(
                route = "${ConverterScreen.UseConverter.name}$CONVERTER_ID_ARG",
                arguments = listOf(navArgument(CONVERTER_ID) {
                    defaultValue = CONVERTER_DEFAULT_ID
                })
            ) {
                UseConverterScreen(
                    converterId = it.arguments?.getString(CONVERTER_ID) ?: CONVERTER_DEFAULT_ID
                )
            }
        }
    }
}

