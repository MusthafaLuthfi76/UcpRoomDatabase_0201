package com.example.ucp2.repository

import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMK {
    suspend fun insertMK(mataKuliah: MataKuliah)

    fun getAllMK (): Flow<List<MataKuliah>>

    fun getMK(kode: String): Flow<MataKuliah>

    suspend fun deleteMK (mataKuliah: MataKuliah)

    suspend fun updateMK(mataKuliah: MataKuliah)
}