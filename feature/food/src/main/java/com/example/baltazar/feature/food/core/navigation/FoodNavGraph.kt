package com.example.baltazar.feature.food.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.baltazar.core.core.navigation.FoodCompanyDetail
import com.example.baltazar.core.core.navigation.FoodCompanyList
import com.example.baltazar.core.core.navigation.FoodDetail
import com.example.baltazar.core.core.navigation.FoodList
import com.example.baltazar.feature.food.ui.screens.food_detail.FoodDetailScreen
import com.example.baltazar.feature.food.ui.screens.foods.FoodsScreen

fun NavGraphBuilder.foodNavGraph(navController: NavHostController) {
    composable<FoodList> { FoodsScreen(navController) }
    composable<FoodDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<FoodDetail>()
        FoodDetailScreen(navController)
    }
    composable<FoodCompanyList> { FoodsScreen(navController) }
    composable<FoodCompanyDetail> { backStackEntry ->
        val args = backStackEntry.toRoute<FoodCompanyDetail>()
        FoodDetailScreen(navController) 
    }
}
