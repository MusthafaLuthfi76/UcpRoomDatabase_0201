package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.database.UcpDatabase
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryDsn
import com.example.ucp2.repository.RepositoryMK
import com.example.ucp2.ui.viewmodel.dosen.DosenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MKViewModel(
    private val repositoryMK: RepositoryMK,
) : ViewModel(){

    var uiState by mutableStateOf(MKUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState(mkEvent: MKEvent){
        uiState = uiState.copy(
            mkEvent = mkEvent,
        )
    }

    // Validasi data input pengguna
    private fun validatefields(): Boolean {
        val event = uiState.mkEvent
        val errorState = MKFormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "JenisMK tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.mkEvent
        if (validatefields()) {
            viewModelScope.launch {
                try {
                    repositoryMK.insertMK(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        mkEvent = MKEvent(), // Reset Input Form
                        isEntryValid = MKFormErrorState() // Reset Error state
                    )
                }
                catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak Valid. Periksa kembali data anda"
            )
        }
    }

    //Reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class MKUIState(
    val mkEvent: MKEvent = MKEvent(),
    val isEntryValid: MKFormErrorState = MKFormErrorState(),
    val snackBarMessage : String? = null
)

data class MKFormErrorState(
    val kode : String? = null,
    val nama : String? = null,
    val sks: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
){
    fun isValid(): Boolean{
        return kode == null && nama == null && sks == null &&
                semester == null && jenis == null && dosenPengampu == null
    }
}

//data class Variabel yang menyimpan data input form
data class MKEvent(
    val kode : String = "",
    val nama : String = "",
    val sks : String = "",
    val semester : String = "",
    val jenis : String = "",
    val dosenPengampu : String = ""
)

// Menyimpan input form ke dalam entity
fun MKEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama,
    sks = sks,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)