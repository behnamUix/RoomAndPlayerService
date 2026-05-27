package com.behnamuix.myapplication04.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.behnamuix.myapplication04.ui.navigation.screens.post.PostAddSc
import com.behnamuix.myapplication04.ui.navigation.screens.post.PostDetailSc
import com.behnamuix.myapplication04.ui.navigation.screens.PostFeedSc

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.PostFeed.route,

        ) {
        composable(Screens.PostFeed.route) {
            PostFeedSc(navController = navController)
        }
        composable(
            route= Screens.PostAdd.routeWithArgs("0:id"),
            arguments = listOf(
                navArgument("0"){
                    type= NavType.StringType
                    defaultValue="0"
                }
            )
        )
        {
            val id=it.arguments?.getInt("0")?:"0"
            PostAddSc(navController = navController)
        }
        composable(Screens.PostDetail.route) {
            PostDetailSc(navController = navController)
        }

    }

}

