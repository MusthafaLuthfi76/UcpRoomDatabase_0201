package com.example.ucp2.ui.viewmodel.dosen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.repository.RepositoryDsn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DosenViewModel(private val repositoryDsn: RepositoryDsn) : ViewModel() {

    var uiState by mutableStateOf(DosenUIState())

    // Memperbarui state berdasarkan input pengguna
    fun updateState(dosenEvent: DosenEvent){
        uiState = uiState.copy(
            dosenEvent = dosenEvent,
        )
    }

    // Validasi data input pengguna
    private fun validatefields(): Boolean {
        val event = uiState.dosenEvent
        val errorState = DosenFormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = uiState.dosenEvent
        if (validatefields()) {
            viewModelScope.launch {
                try {
                    repositoryDsn.insertDsn(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil Disimpan",
                        dosenEvent = DosenEvent(), // Reset Input Form
                        isEntryValid = DosenFormErrorState() // Reset Error state
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

data class DosenUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val dosenList: List<Dosen> = emptyList(),
    val isEntryValid: DosenFormErrorState = DosenFormErrorState(),
    val snackBarMessage: String? = null
)

data class DosenFormErrorState(
    val nidn: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null
) {
    fun isValid(): Boolean {
        return nidn == null && nama == null && jenisKelamin == null
    }
}

// Data class untuk form input
data class DosenEvent(
    val nidn: String = "",
    val nama: String = "",
    val jenisKelamin: String = ""
)

// Mapping dari event ke entity
fun DosenEvent.toDosenEntity(): Dosen {
    return Dosen(
        nidn = this.nidn,
        nama = this.nama,
        jenisKelamin = this.jenisKelamin
    )
}

