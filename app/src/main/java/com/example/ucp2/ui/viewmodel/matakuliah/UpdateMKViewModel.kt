package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMK
import com.example.ucp2.ui.navigation.DestinasiUpdateMK
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMK: RepositoryMK
) : ViewModel() {

    var updateUIState by mutableStateOf(MKUIState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMK.KODE])

    init {
        viewModelScope.launch {
            updateUIState = repositoryMK.getMK(_kode)
                .filterNotNull()
                .first()
                .toUIStateMK()
        }
    }

    fun updateState(mahasiswaEvent: MKEvent){
        updateUIState = updateUIState.copy(
            mkEvent = mahasiswaEvent,
        )
    }

    fun validateFields(): Boolean {
        val event = updateUIState.mkEvent
        val errorState = MKFormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "Kode tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "JenisMK tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong"
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEvent = updateUIState.mkEvent

        if (validateFields()){
            viewModelScope.launch{
                try {
                    repositoryMK.updateMK(currentEvent.toMataKuliahEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        mkEvent = MKEvent(),
                        isEntryValid = MKFormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackBarMessage}")
                }
                catch (e: Exception){
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun MataKuliah.toUIStateMK(): MKUIState = MKUIState(
    mkEvent = this.toDetailUiEventMK(),
)