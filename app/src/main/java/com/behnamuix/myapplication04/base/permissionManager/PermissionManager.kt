package com.behnamuix.myapplication04.base.permissionManager

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionManager(val context: Context){
    fun isGranted(perm:String): Boolean{
        return ContextCompat.checkSelfPermission(context,perm)== PackageManager.PERMISSION_GRANTED
    }
}