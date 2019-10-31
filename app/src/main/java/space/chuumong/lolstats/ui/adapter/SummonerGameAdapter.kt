package space.chuumong.lolstats.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import space.chuumong.data.const.SUMMONER_FIVE_TO_FIVE_RANK
import space.chuumong.data.const.SUMMONER_SOLO_RANK_TYPE
import space.chuumong.data.utils.DateUtil
import space.chuumong.data.utils.GameDateType
import space.chuumong.data.utils.empty
import space.chuumong.domain.entities.OpScoreBadge
import space.chuumong.domain.entities.SummonerGame
import space.chuumong.lolstats.R
import space.chuumong.lolstats.ui.utils.*

class SummonerGameAdapter : RecyclerView.Adapter<SummonerGameAdapter.SummonerGameViewHolder>() {

    private val games = mutableListOf<SummonerGame>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummonerGameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return SummonerGameViewHolder(view)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: SummonerGameViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int) = games[position]

    fun addAll(items: List<SummonerGame>) {
        games.clear()
        games.addAll(items)
        notifyDataSetChanged()
    }

    inner class SummonerGameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val IMAGE_RADIUS = 4

        private val clWinAndLoss = view.findViewById<ConstraintLayout>(R.id.cl_win_and_loss)
        private val tvWinAndLoss = view.findViewById<TextView>(R.id.tv_win_and_loss)

        private val tvTime = view.findViewById<TextView>(R.id.tv_time)

        private val ivChampion = view.findViewById<ImageView>(R.id.iv_champion)
        private val tvOpScoreBadge = view.findViewById<TextView>(R.id.tv_op_score_badge)

        private val ivFirstSpell = view.findViewById<ImageView>(R.id.iv_first_spell)
        private val ivSecondSpell = view.findViewById<ImageView>(R.id.iv_second_spell)

        private val ivMainRun = view.findViewById<ImageView>(R.id.iv_main_run)
        private val ivSubRun = view.findViewById<ImageView>(R.id.iv_sub_run)

        private val tvKillsDeathsAssists = view.findViewById<TextView>(R.id.tv_kills_deaths_assists)
        private val tvKillRate = view.findViewById<TextView>(R.id.tv_kill_rate)

        private val ivItems: List<ImageView> = listOf(
            view.findViewById(R.id.iv_1st_item),
            view.findViewById(R.id.iv_2nd_item),
            view.findViewById(R.id.iv_3r_item),
            view.findViewById(R.id.iv_4th_item),
            view.findViewById(R.id.iv_5th_item),
            view.findViewById(R.id.iv_6th_item)
        )
        private val ivWard = view.findViewById<ImageView>(R.id.iv_ward)

        private val tvGameType = view.findViewById<TextView>(R.id.tv_game_type)
        private val tvGameDate = view.findViewById<TextView>(R.id.tv_game_date)
        private val tvMultiKill = view.findViewById<TextView>(R.id.tv_multi_kill)

        private val context = itemView.context

        fun bind(item: SummonerGame) {
            if (item.isWin) {
                clWinAndLoss.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_01))
                tvWinAndLoss.text = context.getString(R.string.summoner_win)
            } else {
                clWinAndLoss.setBackgroundColor(ContextCompat.getColor(context, R.color.red_01))
                tvWinAndLoss.text = context.getString(R.string.summoner_loss)
            }

            tvTime.text = DateUtil.getGameTime(item.gameLength)

            ivChampion.loadCircleImage(item.champion.imageUrl)
            if (item.stats.general.opScoreBadge.isNotEmpty()) {
                tvOpScoreBadge.visibility = View.VISIBLE
                tvOpScoreBadge.text = item.stats.general.opScoreBadge
                tvOpScoreBadge.setBackgroundResource(
                    when (item.stats.general.opScoreBadge) {
                        OpScoreBadge.ACE.name -> R.drawable.shape_stroke_white_solid_purple_01_corner_8
                        OpScoreBadge.MVP.name -> R.drawable.shape_stroke_white_solid_yellow_01_corner_8
                        else -> R.drawable.shape_stroke_white_solid_purple_01_corner_8
                    }
                )
            } else {
                tvOpScoreBadge.visibility = View.GONE
            }

            ivFirstSpell.loadRoundImage(item.spells[0].imageUrl, IMAGE_RADIUS.toPx())
            ivSecondSpell.loadRoundImage(item.spells[1].imageUrl, IMAGE_RADIUS.toPx())

            ivMainRun.loadCircleImage(item.peak[0])
            ivSubRun.loadCircleImage(item.peak[1])

            tvKillsDeathsAssists.setKillDeathAssistAverage(
                item.stats.general.kills.toString(),
                item.stats.general.deaths.toString(),
                item.stats.general.assists.toString()
            )

            tvKillRate.text = String.format(
                context.getString(R.string.summoner_kill_rate),
                item.stats.general.killRate
            )

            ivItems.forEach { it.setImageResource(R.drawable.shape_solid_gray_01_corner_4) }

            for (i in item.items.indices) {
                ivItems[i].loadRoundImage(item.items[i].imageUrl, IMAGE_RADIUS.toPx())
            }

            ivWard.isVisible = item.hasWard
            if (item.hasWard) {
                ivWard.loadCircleImage(item.wardImageUrl)
            }

            tvGameType.text = when (item.gameType) {
                SUMMONER_SOLO_RANK_TYPE -> context.getString(R.string.summoner_solo_rank)
                SUMMONER_FIVE_TO_FIVE_RANK -> context.getString(R.string.summoner_five_to_five_rank)
                else -> String.empty()
            }

            val gameDate = DateUtil.getMatchGameDate(item.createDate)
            tvGameDate.text = when (gameDate.first) {
                GameDateType.MIN -> String.format(
                    context.getString(R.string.before_min_format),
                    gameDate.second
                )
                GameDateType.HOUR -> String.format(
                    context.getString(R.string.before_hour_format),
                    gameDate.second
                )
                GameDateType.DATE -> gameDate.second
                else -> String.empty()
            }

            if (item.stats.general.multiKill.isNotEmpty()) {
                tvMultiKill.visibility = View.VISIBLE
                tvMultiKill.text = item.stats.general.multiKill
            } else {
                tvMultiKill.visibility = View.GONE
            }
        }
    }
}