package com.example.ucp2.ui.viewmodel.dosen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DosenViewModel(private val dosenDao: DosenDao) : ViewModel() {

    private val _uiState = MutableStateFlow(DosenUIState())
    val uiState: StateFlow<DosenUIState> get() = _uiState

    // Memperbarui state berdasarkan input pengguna
    fun updateState(dosenEvent: DosenEvent) {
        _uiState.value = _uiState.value.copy(
            dosenEvent = dosenEvent
        )
    }

    // Validasi input form
    private fun validateFields(): Boolean {
        val event = _uiState.value.dosenEvent
        val errorState = DosenFormErrorState(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        _uiState.value = _uiState.value.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    // Menyimpan data dosen
    fun saveDosen() {
        if (validateFields()) {
            val currentEvent = _uiState.value.dosenEvent
            viewModelScope.launch {
                try {
                    dosenDao.insertDosen(currentEvent.toDosenEntity())
                    _uiState.value = _uiState.value.copy(
                        snackBarMessage = "Dosen berhasil disimpan",
                        dosenEvent = DosenEvent(), // Reset form
                        isEntryValid = DosenFormErrorState() // Reset error state
                    )
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(
                        snackBarMessage = "Gagal menyimpan dosen"
                    )
                }
            }
        } else {
            _uiState.value = _uiState.value.copy(
                snackBarMessage = "Input tidak valid, periksa kembali"
            )
        }
    }

    // Mendapatkan semua dosen
    fun fetchDosenList() {
        viewModelScope.launch {
            dosenDao.getAllDosen().collect { dosenList ->
                _uiState.value = _uiState.value.copy(dosenList = dosenList)
            }
        }
    }

    // Reset pesan snackbar
    fun resetSnackBarMessage() {
        _uiState.value = _uiState.value.copy(snackBarMessage = null)
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

