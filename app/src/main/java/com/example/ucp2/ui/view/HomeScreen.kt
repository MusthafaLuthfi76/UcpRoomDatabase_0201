package com.example.ucp2.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.ucp2.R

@Composable
fun HomeScreen(
    navigateToDosen: () -> Unit, // Navigasi ke halaman Dosen
    navigateToMK: () -> Unit // Navigasi ke halaman Mata Kuliah
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.images2
                ),
                contentDescription = "",
                Modifier.size(175.dp)
            )
            // Judul atau Header
            Text(
                text = "UCP2 Aplikasi Mobile",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = "Pilih menu yang ingin Anda akses:",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Tombol untuk ke halaman Dosen
            Button(
                onClick = navigateToDosen,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Dosen",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                )
            }

            // Tombol untuk ke halaman Mata Kuliah
            Button(
                onClick = navigateToMK,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            ) {
                Text(
                    text = "Mata Kuliah",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navigateToDosen = { /* TODO: Implementasikan navigasi ke halaman Dosen */ },
        navigateToMK = { /* TODO: Implementasikan navigasi ke halaman Mata Kuliah */ }
    )
}
