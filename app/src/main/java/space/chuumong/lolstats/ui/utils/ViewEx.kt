package space.chuumong.lolstats.ui.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View

fun Activity.setLightStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}