package space.chuumong.lolstats.binding

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName
import space.chuumong.data.utils.empty
import space.chuumong.domain.entities.SummonerPositionType
import space.chuumong.lolstats.R
import space.chuumong.lolstats.ui.utils.loadCircleImage
import space.chuumong.lolstats.ui.utils.setKillDeathAssistAverage

@BindingAdapter("loadCircleImage")
fun setLoadCircleImage(iv: ImageView, url: String?) {
    if (url == null) {
        iv.setImageResource(0)
        return
    }

    iv.loadCircleImage(url)
}

@BindingAdapter("recentGameCount")
fun setRecentGameCount(tv: TextView, count: Int?) {
    if (count == null) {
        tv.text = String.empty()
        return
    }

    tv.text = String.format(
        tv.context.getString(R.string.summoner_recent_analysis_game_count_format),
        count
    )
}

@BindingAdapter(value = ["bind:resentWinCount", "bind:resentLossCount"])
fun setResentWinAndLoss(tv: TextView, winCount: Int?, lossCount: Int?) {
    if (winCount == null || lossCount == null) {
        tv.text = String.empty()
        return
    }

    tv.text = String.format(
        tv.context.getString(R.string.summoner_resent_win_and_loss_format),
        winCount,
        lossCount
    )
}

@BindingAdapter(value = ["bind:killAverage", "bind:deathAverage", "bind:assistAverage"])
fun setKillDeathAssistAverageText(
    tv: TextView,
    killAverage: Float?,
    deathAverage: Float?,
    assistAverage: Float?
) {
    if (killAverage == null || deathAverage == null || assistAverage == null) {
        tv.text = String.empty()
        return
    }

    val killAverageText = String.format("%.1f", killAverage)
    val deathAverageText = String.format("%.1f", deathAverage)
    val assistAverageText = String.format("%.1f", assistAverage)

    tv.setKillDeathAssistAverage(killAverageText, deathAverageText, assistAverageText)
}

@BindingAdapter("kdaText")
fun setKdaText(tv: TextView, kda: Float?) {
    if (kda == null) {
        tv.text = String.empty()
        return
    }

    tv.text = String.format(tv.context.getString(R.string.kda_format), kda)
}

@BindingAdapter("resentWinRateText")
fun setResentWinRateText(tv: TextView, winRate: Int?) {
    if (winRate == null) {
        tv.text = String.empty()
        return
    }

    tv.text = String.format(tv.context.getString(R.string.resent_win_rate), winRate)
}

@BindingAdapter("summonerPositionImage")
fun setSummonerPositionImage(iv: ImageView, position: String?) {
    if (position == null) {
        iv.setImageResource(0)
        return
    }

    iv.setImageResource(
        when (position) {
            SummonerPositionType.TOP.name -> R.drawable.icon_lol_top
            SummonerPositionType.JNG.name -> R.drawable.icon_lol_jng
            SummonerPositionType.MID.name -> R.drawable.icon_lol_mid
            SummonerPositionType.ADC.name -> R.drawable.icon_lol_bot
            SummonerPositionType.SUP.name -> R.drawable.icon_lol_sup
            else -> R.drawable.icon_lol_sup
        }
    )
}

@BindingAdapter("winRateText")
fun setWinRateText(tv: TextView, winRate: Int?) {
    if (winRate == null) {
        tv.text = String.empty()
        return
    }
    
    tv.text = String.format(tv.context.getString(R.string.win_rate), winRate)
}