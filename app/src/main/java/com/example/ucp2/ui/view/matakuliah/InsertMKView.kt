package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.customTopAppBar
import com.example.ucp2.ui.navigation.AlamatNavigasi
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.MKEvent
import com.example.ucp2.ui.viewmodel.matakuliah.MKFormErrorState
import com.example.ucp2.ui.viewmodel.matakuliah.MKUIState
import com.example.ucp2.ui.viewmodel.matakuliah.MKViewModel
import kotlinx.coroutines.launch
import com.example.ucp2.R
import com.example.ucp2.data.DosenDD
import com.example.ucp2.ui.customwidget.DynamicSelectTextField
import com.example.ucp2.ui.view.HomeScreen

object DestinasiInsert : AlamatNavigasi {
    override val route: String = "inset_mk"
}

@Composable
fun InsertMKView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MKViewModel = viewModel (factory = PenyediaViewModel.Factory) //Inisialisasi View Model
) {
    val uiState = viewModel.uiState // Ambil UI State dari ViewModel
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar State
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch{
                snackbarHostState.showSnackbar(message) // Tampilkan Snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Tempatkan Snackbar di Scaffold
    ){
            padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            //Isi Body
            InsertBodyMK (
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) // Update state di viewmodel
                },
                onClick = {
                    coroutineScope.launch{
                        viewModel.saveData() //Simpan Data
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMK (
    modifier: Modifier = Modifier,
    onValueChange: (MKEvent) -> Unit,
    uiState: MKUIState,
    onClick: () -> Unit
) {
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement =  Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            mkEvent = uiState.mkEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormMatakuliah(
    mkEvent: MKEvent = MKEvent(),
    onValueChange: (MKEvent) -> Unit,
    errorState: MKFormErrorState = MKFormErrorState(),
    modifier: Modifier = Modifier
){
    val jenisMK = listOf("MK Wajib", "MK Peminatan")
    val semester = listOf("1", "2", "3", "4", "5", "6")
    var chosenDropdown by remember {
        mutableStateOf("")
    }

    Column (modifier = Modifier
        .fillMaxSize()
        .background(
            color = colorResource(id = R.color.primary)
        )
    )
    {
        Row(
            modifier = Modifier
                .padding(40.dp)
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.images2
                ),
                contentDescription = "",
            )

            Spacer(modifier = Modifier.padding(start = 20.dp))

            Column {
                Text(
                    text = "Universitas Muhammadiyah Yogyakarta",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Unggul dan Islami",
                    color = Color.White,
                    fontWeight = FontWeight.Light
                )
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                Color.White,
                shape = RoundedCornerShape(
                    topEnd = 15.dp,
                    topStart = 15.dp
                )
            ))
        {
            Column (
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Masukkan Data Mata Kuliah",
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp
                )

                Text(
                    text = "Isi Sesuai Data yang Kamu Daftarkan",
                    fontWeight = FontWeight.Light
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = ""
                        )
                    },
                    value = mkEvent.nama,
                    onValueChange = {
                        onValueChange(mkEvent.copy(nama = it))
                    },
                    label = { Text("Nama") },
                    isError = errorState.nama != null,
                    placeholder = {Text("Masukkan Nama MK")},
                )
                Text(
                    text = errorState.nama?: "",
                    color = Color.Red
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = ""
                        )
                    },
                    value = mkEvent.kode, onValueChange = {
                        onValueChange(mkEvent.copy(kode = it))
                    } ,
                    label = { Text("Kode MK") },
                    isError = errorState.kode != null,
                    placeholder = {Text("Masukkan Kode MK")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                )
                Text(text = errorState.kode ?: "", color = Color.Red)

                Text(text = "Jenis Mata Kuliah")
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    jenisMK.forEach{jmk ->
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = mkEvent.jenis ==jmk,
                                onClick = {
                                    onValueChange(mkEvent.copy(jenis = jmk))
                                },
                            )
                            Text(
                                text = jmk,
                            )
                        }
                    }
                }
                Text(
                    text = errorState.jenis ?: "",
                    color = Color.Red
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = ""
                        )
                    },
                    value = mkEvent.sks, onValueChange = {
                        onValueChange(mkEvent.copy(sks = it))
                    } ,
                    label = { Text("SKS") },
                    isError = errorState.sks != null,
                    placeholder = {Text("Masukkan SKS")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                )
                Text(text = errorState.sks ?: "", color = Color.Red)


                Text(text = "Semester")
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    semester.forEach{semester ->
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = mkEvent.semester == semester,
                                onClick = {
                                    onValueChange(mkEvent.copy(semester = semester))
                                },
                            )
                            Text(
                                text = semester,
                            )
                        }
                    }
                }
                Text(
                    text = errorState.semester ?: "",
                    color = Color.Red
                )

                val dosenNames = DosenDD.option.collectAsState(initial = emptyList()).value.map { it.nama }
                DynamicSelectTextField(
                    selectedValue = chosenDropdown,
                    options = dosenNames,
                    label = "Dosen Pengampu",
                    onValueChangedEvent = {
                        chosenDropdown = it
                        onValueChange(mkEvent.copy(dosenPengampu = it))
                    },
                    )
                Text(
                    text = errorState.dosenPengampu ?: "",
                    color = Color.Red
                )
            }
        }
    }
}


