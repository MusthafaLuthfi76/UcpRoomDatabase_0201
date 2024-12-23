package com.example.ucp2.ui.view.dosen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.ui.customwidget.customTopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.dosen.HomeUiState
import com.example.ucp2.ui.viewmodel.dosen.TampilDsnViewModel
import kotlinx.coroutines.launch

@Composable
fun TampilDsnView(
    viewModel: TampilDsnViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDsn: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold (
        topBar = {
            customTopAppBar(
                judul = "Daftar Dosen",
                showBackButton = false,
                onBack = { },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddDsn,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Dosen",
                )
            }
        }
    ) { innerPadding ->
        val homeUiState by viewModel.homeUiState.collectAsState()

        BodyTampilDsnView(
            homeUiState = homeUiState,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )

    }
}

@Composable
fun BodyTampilDsnView(
    homeUiState: HomeUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } //Snackbar state
    when {
        homeUiState.isLoading -> {
            // Menampilkan indikator loading
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeUiState.isError -> {
            // Menampilkan pesan error
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)//Tampilkan snackbar
                    }
                }
            }
        }

        homeUiState.listDsn.isEmpty() -> {
            // Menampilkan pesan jika data kosong
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data Dosen",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            //Menampilkan daftar mahasiswa
            ListDosen(
                listDsn = homeUiState.listDsn,
                onClick = {
                    onClick(it)
                    println(
                        it
                    )
                },
                modifier = modifier
            )
        }
    }

}

@Composable
fun ListDosen(
    listDsn: List<Dosen>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn (
        modifier = modifier
    ){
        items(
            items = listDsn,
            itemContent = {dsn ->
                CardDsn(
                    dsn = dsn,
                    onClick = {onClick(dsn.nidn)}
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardDsn (
    dsn: Dosen,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tambahkan icon di sisi kiri
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Dosen Icon",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(48.dp) // Ukuran ikon
            )

            Column(
                modifier = Modifier.weight(1f) // Mengambil ruang sisa
            ) {
                Text(
                    text = dsn.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    text = "NIDN: ${dsn.nidn}",
                    fontSize = 16.sp
                )
                Text(
                    text = "Jenis Kelamin: ${dsn.jenisKelamin}",
                    fontSize = 14.sp
                )
            }
        }
    }
}