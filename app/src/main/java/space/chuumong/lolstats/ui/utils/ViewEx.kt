package space.chuumong.lolstats.ui.utils

import android.app.Activity
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun Activity.setLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun Activity.showNoTitleTwoButtonsDialog(
    message: String?,
    positiveButtonLabel: String,
    positiveButtonListener: () -> Unit,
    negativeButtonLabel: String,
    negativeButtonListener: () -> Unit
) {
    AlertDialog.Builder(this).setMessage(message)
        .setPositiveButton(positiveButtonLabel) { _, _ -> positiveButtonListener() }
        .setNegativeButton(negativeButtonLabel) { _, _ -> negativeButtonListener() }
        .setCancelable(false)
        .create()
        .show()
}

fun Int.toPx(): Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}