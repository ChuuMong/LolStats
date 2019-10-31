package space.chuumong.data.utils

import java.text.SimpleDateFormat
import java.util.*

enum class GameDateType {
    MIN, HOUR, DATE
}

object DateUtil {
    private const val SEC = 60
    private const val MIN = 60
    private const val HOUR = 24

    private const val MILL_SEC = 1000L

    private const val MIN_AND_SEC_FORMAT = "%02d:%02d"

    private const val DATE_FORMAT = "yyyy.MM.dd"

    fun getGameTime(time: Int): String {
        val min = time / MIN
        val sec = time % SEC

        return String.format(MIN_AND_SEC_FORMAT, min, sec)
    }

    fun getMatchGameDate(date: Int): Pair<GameDateType, String> {
        val calender = Calendar.getInstance()
        var diffTime = ((calender.timeInMillis / MILL_SEC) - date) / MIN

        if (diffTime < MIN) {
            return Pair(GameDateType.MIN, diffTime.toString())
        }

        diffTime /= MIN

        if (diffTime < HOUR) {
            return Pair(GameDateType.HOUR, diffTime.toString())
        }

        val date = SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(Date(date * MILL_SEC))
        return Pair(GameDateType.DATE, date)
    }
}