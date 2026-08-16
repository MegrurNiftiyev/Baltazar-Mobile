package com.example.baltazar.feature.company.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.baltazar.core.core.navigation.CompanyDetail
import com.example.baltazar.core.core.navigation.CompanyList
import com.example.baltazar.feature.company.ui.screens.company_detail.CompanyDetailScreen
import com.example.baltazar.feature.company.ui.screens.company_list.CompanyListScreen

fun NavGraphBuilder.companyNavGraph(navController: NavHostController) {
    composable<CompanyList> { CompanyListScreen(navController) }
    composable<CompanyDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<CompanyDetail>()
        CompanyDetailScreen(navController = navController)
    }
}
