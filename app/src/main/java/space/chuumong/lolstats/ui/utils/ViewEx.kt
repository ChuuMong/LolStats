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
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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

fun ImageView.loadCircleImage(url: String) {
    Glide.with(context).load(url).apply(RequestOptions().circleCrop()).into(this)
}

fun ImageView.loadRoundImage(url: String, radius: Int) {
    Glide.with(context).load(url)
        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(radius))).into(this)
}

fun TextView.setKillDeathAssistAverage(kill: String, death: String, assist: String) {
    val spaceSlash = " / "

    val deathAverageTextStartIndex = kill.length + spaceSlash.length
    val assistAverageTextStartIndex =
        deathAverageTextStartIndex + death.length + spaceSlash.length

    val averageText =
        kill + spaceSlash + death + spaceSlash + assist
    val spannableBuilder = SpannableStringBuilder(averageText)
    spannableBuilder.setSpan(
        StyleSpan(Typeface.BOLD),
        0,
        kill.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableBuilder.setSpan(
        StyleSpan(Typeface.BOLD),
        deathAverageTextStartIndex,
        deathAverageTextStartIndex + death.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableBuilder.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(context, R.color.red_01)),
        deathAverageTextStartIndex,
        deathAverageTextStartIndex + death.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    spannableBuilder.setSpan(
        StyleSpan(Typeface.BOLD),
        assistAverageTextStartIndex,
        assistAverageTextStartIndex + assist.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    text = spannableBuilder
}