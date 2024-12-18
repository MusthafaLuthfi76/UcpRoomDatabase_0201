package com.example.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome: AlamatNavigasi{
    override val route = "home"
}

object DestinasiDetail : AlamatNavigasi{
    override val route = "detail"
    const val NIDN = "nidn"
    val routesWithArg = "$route/{$NIDN}"
}

object DestinasiUpdate : AlamatNavigasi {
    override val route = "update"
    const val NIDN = "nidn"
    val  routesWithArg = "$route/{$NIDN}"
}