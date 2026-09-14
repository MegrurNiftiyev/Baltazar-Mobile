package com.example.baltazar.feature.company.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.core.navigation.CompanyDetail
import com.example.baltazar.core.core.navigation.CompanyList
import com.example.baltazar.core.core.navigation.FoodCompanyDetail
import com.example.baltazar.core.core.navigation.FoodCompanyList
import com.example.baltazar.core.core.navigation.HotelCompanyDetail
import com.example.baltazar.core.core.navigation.HotelCompanyList
import com.example.baltazar.core.core.navigation.RentACarCompanyDetail
import com.example.baltazar.core.core.navigation.RentACarCompanyList
import com.example.baltazar.core.core.navigation.TravelCompanyDetail
import com.example.baltazar.core.core.navigation.TravelCompanyList
import com.example.baltazar.feature.company.ui.screens.company_detail.CompanyDetailScreen
import com.example.baltazar.feature.company.ui.screens.company_list.CompanyListScreen

fun NavGraphBuilder.companyNavGraph(navController: NavHostController) {
    composable<CompanyList> { backStackEntry ->
        val args = backStackEntry.toRoute<CompanyList>()
        val serviceType = args.serviceType?.let { typeStr ->
            try { ServiceType.valueOf(typeStr.uppercase()) } catch (_: Exception) { null }
        }
        CompanyListScreen(
            navController = navController,
            serviceType = serviceType
        )
    }
    composable<CompanyDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<CompanyDetail>()
        CompanyDetailScreen(
            navController = navController,
            companyId = args.id
        )
    }

    composable<FoodCompanyList> {
        CompanyListScreen(navController = navController, serviceType = ServiceType.FOOD)
    }
    composable<FoodCompanyDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<FoodCompanyDetail>()
        CompanyDetailScreen(navController = navController, companyId = args.id)
    }

    composable<HotelCompanyList> {
        CompanyListScreen(navController = navController, serviceType = ServiceType.HOTEL)
    }
    composable<HotelCompanyDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<HotelCompanyDetail>()
        CompanyDetailScreen(navController = navController, companyId = args.id)
    }

    composable<TravelCompanyList> {
        CompanyListScreen(navController = navController, serviceType = ServiceType.TRAVEL)
    }
    composable<TravelCompanyDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<TravelCompanyDetail>()
        CompanyDetailScreen(navController = navController, companyId = args.id)
    }

    composable<RentACarCompanyList> {
        CompanyListScreen(navController = navController, serviceType = ServiceType.RENT_A_CAR)
    }
    composable<RentACarCompanyDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<RentACarCompanyDetail>()
        CompanyDetailScreen(navController = navController, companyId = args.id)
    }
}
