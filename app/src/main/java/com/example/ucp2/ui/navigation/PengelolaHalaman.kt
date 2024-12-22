package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.HomeScreen
import com.example.ucp2.ui.view.dosen.DestinasiInsert
import com.example.ucp2.ui.view.dosen.DetailDsnView
import com.example.ucp2.ui.view.dosen.InsertDsnView
import com.example.ucp2.ui.view.dosen.TampilDsnView
import com.example.ucp2.ui.view.matakuliah.DetailMKView
import com.example.ucp2.ui.view.matakuliah.HomeMKView
import com.example.ucp2.ui.view.matakuliah.InsertMKView
import com.example.ucp2.ui.view.matakuliah.UpdateMKView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost (
        navController = navController,
        startDestination = DestinasiHomeScreen.route,
        modifier = modifier
    ) {

        composable(
            route = DestinasiHomeScreen.route
        ) {
            HomeScreen (
                navigateToDosen = {
                    navController.navigate(DestinasiHome.route)
                },
                navigateToMK = {
                    navController.navigate(DestinasiHomeMK.route)
                },
            )
        }
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

        composable(DestinasiHomeMK.route) {
            HomeMKView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetailMK.route}/$kode")
                },
                onAddMk = {
                    navController.navigate(DestinasiInsertMK.route)
                },
                modifier = modifier
            )
        }

        composable(
            route = DestinasiDetailMK.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMK.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            val mkId = it.arguments?.getString(DestinasiDetailMK.KODE)
            mkId?.let { validMkId ->
                DetailMKView(
                    onBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }

        composable(DestinasiInsertMK.route) {
            InsertMKView(
                onBack = {
                    navController.popBackStack()
                },
                modifier = modifier,
                onNavigate = {
                    navController.popBackStack()
                },
            )
        }

        composable(
            route = DestinasiUpdateMK.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateMK.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            val mkId = it.arguments?.getString(DestinasiUpdateMK.KODE)
            mkId?.let { validMkId ->
                UpdateMKView(
                    onBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier,
                    onNavigate = {
                        navController.popBackStack()
                    },
                )
            }
        }
    }
}