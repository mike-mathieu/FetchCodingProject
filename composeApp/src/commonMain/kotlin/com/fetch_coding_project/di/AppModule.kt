package com.fetch_coding_project.di

import com.fetch_coding_project.view.items.ItemsViewModel
import com.fetch_coding_project.repository.DataRepository
import com.fetch_coding_project.repository.DataRepositoryImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::DataRepositoryImpl) { bind<DataRepository>() }
    viewModelOf(::ItemsViewModel)
}