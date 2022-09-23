package com.example.viewer_2022

import android.content.Context
import android.os.Build
import android.text.Html
import android.widget.Toast

fun showSuccess(context: Context, message: String) {
    showToast(context, message, "#24850f")
}

fun showError(context: Context, message: String) {
    showToast(context, message, "#e61c0e")
}

fun showToast(context: Context, message: String, color: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Toast.makeText(
            context,
            Html.fromHtml(
                "<font color='$color'>$message</font>",
                Html.FROM_HTML_MODE_COMPACT
            ),
            Toast.LENGTH_SHORT
        ).show()
    }
}