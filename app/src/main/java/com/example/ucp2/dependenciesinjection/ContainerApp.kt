package com.example.ucp2.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.UcpDatabase
import com.example.ucp2.repository.LocalRepositoryDsn
import com.example.ucp2.repository.RepositoryDsn

interface InterfaceContainerApp {
    val repositoryDsn: RepositoryDsn
}

class ContainerApp(private val context: Context): InterfaceContainerApp{
    override val repositoryDsn: RepositoryDsn by lazy {
        LocalRepositoryDsn(UcpDatabase.getDatabase(context).dosenDao())
    }
}