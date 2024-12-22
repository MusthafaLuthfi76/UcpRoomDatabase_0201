package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.KrsApp
import com.example.ucp2.ui.viewmodel.dosen.DetailDsnViewModel
import com.example.ucp2.ui.viewmodel.dosen.DosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.TampilDsnViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.DetailMKViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.HomeMKViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.MKViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.UpdateMKViewModel

object PenyediaViewModel {

    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                krsApp().containerApp.repositoryDsn
            )
        }

        initializer {
            TampilDsnViewModel(
                krsApp().containerApp.repositoryDsn
            )
        }

        initializer {
            DetailDsnViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryDsn,
            )
        }

        initializer {
            MKViewModel(
                krsApp().containerApp.repositoryMK
            )
        }

        initializer {
            HomeMKViewModel(
                krsApp().containerApp.repositoryMK
            )
        }

        initializer {
            DetailMKViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMK,
            )
        }

        initializer {
            UpdateMKViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMK,
            )
        }

    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as KrsApp)