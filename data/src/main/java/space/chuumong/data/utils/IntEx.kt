package space.chuumong.data.utils

import java.text.NumberFormat

fun Int.numberFormat(): String {
    return NumberFormat.getInstance().format(this)
}