package com.example.euriskocodechallenge.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import com.example.euriskocodechallenge.R
import com.example.euriskocodechallenge.utils.Constants
import java.io.ByteArrayOutputStream


fun Uri.toBitmap(context: Context): Bitmap {
    try {
        return if (Build.VERSION.SDK_INT < Constants.VERSIONCODE) {
            MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                this
            )
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, this)
            source.let { ImageDecoder.decodeBitmap(it) }
        }
    } catch (e: Exception) {
        UtilityFunctions.printLogs(Constants.TAG, e.message.safe())
    }
    return BitmapFactory.decodeResource(
        context.resources,
        R.drawable.ic_baseline_camera_alt_24
    )
}
fun Bitmap.toBase64(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    val imageBytes = baos.toByteArray()
    return android.util.Base64.encodeToString(imageBytes, android.util.Base64.DEFAULT)
}
