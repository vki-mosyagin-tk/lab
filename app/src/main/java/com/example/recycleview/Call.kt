package com.example.recycleview

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class Call {
    companion object {
        const val REQUEST_CODE = 42
    }

    fun callPhone(phoneNumber: String, con: Context) {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber"))
        startActivity(con, intent, null)
    }

    fun checkPermission(phoneNumber: String, act: Activity, con: Context) {
        if (ContextCompat.checkSelfPermission(
                con,
                Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    act,
                    Manifest.permission.CALL_PHONE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    act,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_CODE
                )
            }
        } else {
            callPhone(phoneNumber, con)
        }
    }
}