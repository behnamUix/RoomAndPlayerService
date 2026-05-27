package com.behnamuix.myapplication04.ui.navigation

sealed class Screens(val route:String){
    object PostFeed: Screens("postFeed")
    object PostAdd: Screens("postAdd")
    object PostDetail: Screens("postDetail")

    fun routeWithArgs(vararg args:String)=
        buildString {
            append(route)
            args.forEachIndexed { index,_->
                append("?$index={$index}")
            }
        }
    fun paramWithArgs(vararg args:String)=
        buildString {
            append(route)
            args.forEachIndexed { index,value->
                append("?$index=$value")
            }
        }


}

