package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.repository.RepositoryMK
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeMKViewModel(
    private val repositoryMK: RepositoryMK
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiStateMK> = repositoryMK.getAllMK()
        .filterNotNull()
        .map {
            HomeUiStateMK(
                listMk = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUiStateMK(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiStateMK(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiStateMK(
                isLoading = true,
            )
        )
}

data class HomeUiStateMK(
    val listMk: List<MataKuliah> = listOf(),
    val isLoading : Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)