package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.dosen.DestinasiInsert
import com.example.ucp2.ui.view.dosen.DetailDsnView
import com.example.ucp2.ui.view.dosen.InsertDsnView
import com.example.ucp2.ui.view.dosen.TampilDsnView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost (
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = modifier
    ) {
        composable(
            route = DestinasiHome.route
        ) {
            TampilDsnView(
                onDetailClick = { nidn ->
                    navController.navigate("${DestinasiDetail.route}/$nidn")
                    println(
                        "PengelolaHalaman : nidn = $nidn"
                    )
                },
                onAddDsn = {
                    navController.navigate(DestinasiInsert.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiInsert.route
        ) {
            InsertDsnView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            DestinasiDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetail.NIDN) {
                    type = NavType.StringType
                }
            )
        ) {
            val nidn = it.arguments?.getString(DestinasiDetail.NIDN)
            nidn?.let { nidn ->
                DetailDsnView(
                    onBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier,
                )
            }
        }
    }
}