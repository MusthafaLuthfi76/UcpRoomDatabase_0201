package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMK
import com.example.ucp2.ui.navigation.DestinasiDetailMK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMKViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMK: RepositoryMK,

    ) : ViewModel(){
    private val  _kode: String = checkNotNull(savedStateHandle[DestinasiDetailMK.KODE])

    val detailUiStatemk: StateFlow<DetailUiStateMK> = repositoryMK.getMK(_kode)
        .filterNotNull()
        .map {
            DetailUiStateMK(
                detailUiEvent = it.toDetailUiEventMK(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiStateMK (isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiStateMK(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",

                    )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiStateMK(
                isLoading = true
            )
        )
    fun deleteMK(){
        detailUiStatemk.value.detailUiEvent.toMataKuliahEntity().let {
            viewModelScope.launch {
                repositoryMK.deleteMK(it)
            }
        }
    }
}

data class DetailUiStateMK(
    val detailUiEvent: MKEvent = MKEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MKEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MKEvent()
}

// Memindahkan data dari entity ke UI
fun MataKuliah.toDetailUiEventMK(): MKEvent{
    return MKEvent(
        kode = kode,
        nama = nama,
        sks = sks,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}