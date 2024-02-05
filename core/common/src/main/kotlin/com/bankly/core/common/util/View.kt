package com.bankly.core.common.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
fun captureAndShareComposeView(context: Context) {
    val rootView = (context as ComponentActivity).findViewById<View>(android.R.id.content)

    val bitmap = Bitmap.createBitmap(
        rootView.width,
        rootView.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bitmap)
    rootView.draw(canvas)

    val imagePath = saveBitmapToFile(bitmap, context)
    if (imagePath != null) {
        shareImage(context, imagePath)
    } else {
        Toast.makeText(context, "Failed to capture and share the view", Toast.LENGTH_SHORT).show()
    }
}

private fun saveBitmapToFile(bitmap: Bitmap, context: Context): String? {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val fileName = "ComposeCapture_$timeStamp.jpg"

    return try {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val imageFile = File(storageDir, fileName)

        val stream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        stream.flush()
        stream.close()

        imageFile.absolutePath
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

private fun shareImage(context: Context, imagePath: String) {
    val imageFile = File(imagePath)
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )

    val shareIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "image/jpeg"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Share using"))
}