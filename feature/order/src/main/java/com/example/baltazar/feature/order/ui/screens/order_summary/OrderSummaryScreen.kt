package com.example.baltazar.feature.order.ui.screens.order_summary

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun OrderSummaryScreen(
    navController: NavHostController,
    viewModel: OrderSummaryViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
    }
}
