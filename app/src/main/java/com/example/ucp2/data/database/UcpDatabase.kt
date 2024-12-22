package com.example.ucp2.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.dao.MataKuliahDao
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.MataKuliah

@Database(entities = [Dosen::class, MataKuliah::class], version = 1, exportSchema = false)
abstract class UcpDatabase: RoomDatabase() {

    //Mendefinisikan fungsi untuk mengakses data mahasiswa
    abstract fun dosenDao(): DosenDao
    abstract fun mkDao(): MataKuliahDao

    companion object{
        @Volatile //Memastikan bahwa nilai variabel instance selalu sama di setiap
        private var Instance: UcpDatabase? = null

        fun getDatabase(context: Context): UcpDatabase{
            return (Instance?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    UcpDatabase::class.java, // Class Database
                    "UcpDatabase" // Nama Database
                )
                    .build().also { Instance = it }
            })
        }
    }
}