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
import space.chuumong.domain.entities.SummonerPositionType
import space.chuumong.lolstats.R
import space.chuumong.lolstats.ui.utils.setKillDeathAssistAverage

@BindingAdapter("loadCircleImage")
fun setLoadCircleImage(iv: ImageView, url: String?) {
    url ?: return

    Glide.with(iv.context).load(url).apply(RequestOptions().circleCrop()).into(iv)
}

@BindingAdapter("recentGameCount")
fun setRecentGameCount(tv: TextView, count: Int?) {
    count ?: return

    tv.text = String.format(
        tv.context.getString(R.string.summoner_recent_analysis_game_count_format),
        count
    )
}

@BindingAdapter(value = ["bind:resentWinCount", "bind:resentLossCount"])
fun setResentWinAndLoss(tv: TextView, winCount: Int?, lossCount: Int?) {
    winCount ?: return
    lossCount ?: return

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
    killAverage ?: return
    deathAverage ?: return
    assistAverage ?: return

    tv.setKillDeathAssistAverage(killAverage, deathAverage, assistAverage)
}

@BindingAdapter("kdaText")
fun setKdaText(tv: TextView, kda: Float?) {
    kda ?: return

    tv.text = String.format(tv.context.getString(R.string.kda_format), kda)
}

@BindingAdapter("resentWinRateText")
fun setResentWinRateText(tv: TextView, winRate: Int?) {
    winRate ?: return

    tv.text = String.format(tv.context.getString(R.string.resent_win_rate), winRate)
}

@BindingAdapter("summonerPositionImage")
fun setSummonerPositionImage(iv: ImageView, position: String?) {
    position ?: return

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
    winRate ?: return

    tv.text = String.format(tv.context.getString(R.string.win_rate), winRate)
}