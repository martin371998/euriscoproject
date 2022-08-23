package com.example.euriskocodechallenge.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.euriskocodechallenge.utils.ConnectivityLiveData
import java.security.AccessControlContext

class Utilityfunctions {
    companion object {
        fun showtoast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

}