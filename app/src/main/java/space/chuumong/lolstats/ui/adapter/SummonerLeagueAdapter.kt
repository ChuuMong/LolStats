package space.chuumong.lolstats.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import space.chuumong.domain.entities.SummonerLeague
import space.chuumong.lolstats.R
import space.chuumong.lolstats.ui.utils.loadUrl
import space.chuumong.lolstats.utils.MathUtils
import space.chuumong.lolstats.utils.numberFormat
import java.text.NumberFormat

class SummonerLeagueAdapter :
    RecyclerView.Adapter<SummonerLeagueAdapter.SummonerLeagueViewHolder>() {

    private val leagues = mutableListOf<SummonerLeague>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummonerLeagueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_league, parent, false)
        return SummonerLeagueViewHolder(view)
    }

    override fun getItemCount() = leagues.size

    override fun onBindViewHolder(holder: SummonerLeagueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getItem(position: Int) = leagues[position]

    fun addAll(items: List<SummonerLeague>) {
        leagues.clear()
        leagues.addAll(items)
        notifyDataSetChanged()
    }

    inner class SummonerLeagueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val ivTire = itemView.findViewById<ImageView>(R.id.iv_tire)
        private val tvLeagueName = itemView.findViewById<TextView>(R.id.tv_league_name)
        private val tvTire = itemView.findViewById<TextView>(R.id.tv_tire)
        private val tvLp = itemView.findViewById<TextView>(R.id.tv_lp)
        private val tvWinRate = itemView.findViewById<TextView>(R.id.tv_win_rate)

        private val context = itemView.context

        fun bind(item: SummonerLeague) {
            ivTire.loadUrl(item.tireRank.tireImageUrl)

            tvLeagueName.text = item.tireRank.name
            tvTire.text = item.tireRank.tire
            tvLp.text =
                String.format(
                    context.getString(R.string.summoner_lp_format),
                    item.tireRank.lp.numberFormat()
                )
            tvWinRate.text = String.format(
                context.getString(R.string.summoner_win_of_loss_rate),
                item.win.numberFormat(),
                item.loss.numberFormat(),
                MathUtils.getWinRate(item.win, item.loss)
            )
        }
    }
}