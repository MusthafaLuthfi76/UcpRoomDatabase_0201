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

object DestinasiUpdateMK : AlamatNavigasi {
    override val route = "updateMK"
    const val KODE = "kode"
    val  routesWithArg = "$route/{$KODE}"
}

object DestinasiDetailMK : AlamatNavigasi{
    override val route = "detailMK"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiHomeScreen : AlamatNavigasi {
    override val route = "homescreen"
}

object DestinasiHomeMK : AlamatNavigasi {
    override val route = "homeMK"
}

object DestinasiInsertMK : AlamatNavigasi {
    override val route = "insertMK"
}