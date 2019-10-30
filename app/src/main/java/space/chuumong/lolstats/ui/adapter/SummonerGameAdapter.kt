package space.chuumong.lolstats.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.chuumong.domain.entities.SummonerGame
import space.chuumong.lolstats.R
import space.chuumong.lolstats.ui.utils.loadUrl
import space.chuumong.lolstats.ui.utils.setKillDeathAssistAverage

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
        private val ivChampion = view.findViewById<ImageView>(R.id.iv_champion)

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
            ivChampion.loadUrl(item.champion.imageUrl)

            ivFirstSpell.loadUrl(item.spells[0].imageUrl)
            ivSecondSpell.loadUrl(item.spells[1].imageUrl)

            ivMainRun.loadUrl(item.peak[0])
            ivSubRun.loadUrl(item.peak[1])

            tvKillsDeathsAssists.setKillDeathAssistAverage(
                item.stats.general.kills.toString(),
                item.stats.general.deaths.toString(),
                item.stats.general.assists.toString()
            )

            tvKillRate.text = String.format(
                context.getString(R.string.summoner_kill_rate),
                item.stats.general.killRate
            )

            ivItems.forEach { it.setImageResource(0) }
            ivWard.setImageResource(0)

            for (i in item.items.indices) {
                ivItems[i].loadUrl(item.items[i].imageUrl)
            }

            if (item.isHasWard) {
                ivWard.loadUrl(item.wardImageUrl)
            }
        }
    }
}