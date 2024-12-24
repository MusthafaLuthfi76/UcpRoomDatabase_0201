package com.example.ucp2.data

import android.content.Context
import com.example.ucp2.data.database.UcpDatabase
import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DosenDD {
    private val _option = MutableStateFlow<List<Dosen>>(emptyList())
    val option: StateFlow<List<Dosen>> = _option

    fun loadData(context: Context) {
        val db = UcpDatabase.getDatabase(context)
        val dosenDao = db.dosenDao()

        // Coroutine untuk memantau perubahan data secara realtime
        CoroutineScope(Dispatchers.IO).launch {
            dosenDao.getAllDosen().collectLatest { dosenList ->
                _option.value = dosenList // Perbarui StateFlow dengan data baru
            }
        }
    }
}