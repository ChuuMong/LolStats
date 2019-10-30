package space.chuumong.lolstats.ui.utils

import android.app.Activity
import android.content.DialogInterface
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import space.chuumong.lolstats.R

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

fun TextView.setKillDeathAssistAverage(
    killAverage: Float, deathAverage: Float, assistAverage: Float
) {
    val killAverageText = String.format("%.1f", killAverage)
    val deathAverageText = String.format("%.1f", deathAverage)
    val assistAverageText = String.format("%.1f", assistAverage)
    val spaceSlash = " / "
    
    val deathAverageTextStartIndex = killAverageText.length + spaceSlash.length
    val assistAverageTextStartIndex =
        deathAverageTextStartIndex + deathAverageText.length + spaceSlash.length

    val averageText =
        killAverageText + spaceSlash + deathAverageText + spaceSlash + assistAverageText
    val spannableBuilder = SpannableStringBuilder(averageText)
    spannableBuilder.setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        killAverageText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableBuilder.setSpan(
        StyleSpan(Typeface.BOLD),
        deathAverageTextStartIndex,
        deathAverageTextStartIndex + deathAverageText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableBuilder.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(context, R.color.red_01)),
        deathAverageTextStartIndex,
        deathAverageTextStartIndex + deathAverageText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableBuilder.setSpan(
        StyleSpan(Typeface.BOLD),
        assistAverageTextStartIndex,
        assistAverageTextStartIndex + assistAverageText.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    text = spannableBuilder
}