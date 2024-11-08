package com.fetch_coding_project

import androidx.compose.runtime.Composable
import com.fetch_coding_project.di.appModule
import com.fetch_coding_project.view.items.ItemsPage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(appModule)
        }
    ) {
        ItemsPage()
    }
}