package space.chuumong.lolstats.utils

object MathUtils {

    fun getWinRate(win: Int, loss: Int): Int {
        if (win == 0) {
            return 0
        }

        return (win.toFloat() / (win + loss).toFloat() * 100).toInt()
    }
}

