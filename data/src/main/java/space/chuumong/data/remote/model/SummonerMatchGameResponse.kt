package space.chuumong.data.remote.model

import com.google.gson.annotations.SerializedName
import space.chuumong.data.utils.MathUtils
import space.chuumong.domain.entities.*

data class SummonerMatchGameResponse(
    @SerializedName("games")
    val games: List<SummonerGameResponse>,
    @SerializedName("champions")
    val mostChampions: List<SummonerMostChampionResponse>,
    @SerializedName("positions")
    val positions: List<SummonerPositionResponse>
) {
    fun toEntity(): SummonerMatchGame {
        val games = mutableListOf<SummonerGame>()
        val mostChampions = mutableListOf<SummonerMostChampion>()
        val positions = mutableListOf<SummonerPosition>()

        var wins = 0
        var losses = 0
        var totalKills = 0
        var totalDeaths = 0
        var totalAssists = 0

        this.games.forEach {
            if (it.isWin) {
                wins += 1
            } else {
                losses += 1
            }

            totalKills += it.stats.general.kills
            totalDeaths += it.stats.general.deaths
            totalAssists += it.stats.general.assists

            games.add(it.toEntity())
        }

        var leastChampion = ""
        var leastWinRate = Int.MAX_VALUE
        this.mostChampions.forEach {

            //TODO 중복 여부 정상 처리 안됨.
            var isAdd = true
            if (mostChampions.find { champion -> champion.name == it.name } != null) {
                isAdd = false
            }

            val winRate = MathUtils.getWinRate(it.wins, it.losses)
            if (winRate < leastWinRate) {
                leastChampion = it.name
                leastWinRate = winRate
            }

            if (isAdd) {
                mostChampions.add(it.toEntity())
            }
        }

        if (mostChampions.size > 2) {
            mostChampions.remove(mostChampions.find { it.name == leastChampion })
        }

        this.positions.forEach { positions.add(it.toEntity()) }

        return SummonerMatchGame(
            games,
            wins,
            losses,
            totalKills,
            totalDeaths,
            totalAssists,
            mostChampions,
            positions
        )
    }
}

data class SummonerGameResponse(
    @SerializedName("champion")
    val champion: SummonerGameChampionResponse,
    @SerializedName("spells")
    val spells: List<ImageUrlResponse>,
    @SerializedName("items")
    val items: List<ImageUrlResponse>,
    @SerializedName("createDate")
    val createDate: Int,
    @SerializedName("gameLength")
    val gameLength: Int,
    @SerializedName("gameType")
    val gameType: String,
    @SerializedName("stats")
    val stats: SummonerGameStatsResponse,
    @SerializedName("peak")
    val peak: List<String>,
    @SerializedName("isWin")
    val isWin: Boolean
) {
    fun toEntity(): SummonerGame {
        val spells = mutableListOf<ImageUrl>()
        val items = mutableListOf<ImageUrl>()

        this.spells.forEach { spells.add(it.toEntity()) }

        var isHasWard = false
        var wardImageUrl = ""
        this.items.forEach {
            if (it.imageUrl.contains("3340")) {
                isHasWard = true
                wardImageUrl = it.imageUrl
                return@forEach
            }

            items.add(it.toEntity())
        }

        return SummonerGame(
            champion.toEntity(),
            spells,
            items,
            isHasWard,
            wardImageUrl,
            createDate,
            gameLength,
            gameType,
            stats.toEntity(),
            peak,
            isWin
        )
    }
}

data class SummonerGameChampionResponse(
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("level")
    val level: Int
) {
    fun toEntity() = SummonerGameChampion(imageUrl, level)
}

data class SummonerGameStatsResponse(
    @SerializedName("general")
    val general: SummonerGameStatsGeneralResponse
) {
    fun toEntity() = SummonerGameStats(general.toEntity())
}

data class SummonerGameStatsGeneralResponse(
    @SerializedName("kill")
    val kills: Int,
    @SerializedName("death")
    val deaths: Int,
    @SerializedName("assist")
    val assists: Int,
    @SerializedName("kdaString")
    val kda: String,
    @SerializedName("contributionForKillRate")
    val killRate: String,
    @SerializedName("largestMultiKillString")
    val multiKill: String,
    @SerializedName("opScoreBadge")
    val opScoreBadge: String
) {
    fun toEntity() =
        SummonerGameStatsGeneral(kills, deaths, assists, kda, killRate, multiKill, opScoreBadge)
}

data class SummonerMostChampionResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("game")
    val game: Int,
    @SerializedName("kills")
    val kills: Int,
    @SerializedName("deaths")
    val deaths: Int,
    @SerializedName("assists")
    val assists: Int,
    @SerializedName("wins")
    val wins: Int,
    @SerializedName("losses")
    val losses: Int
) {
    fun toEntity() =
        SummonerMostChampion(name, imageUrl, game, kills, deaths, assists, wins, losses)
}

data class SummonerPositionResponse(
    @SerializedName("games")
    val games: Int,
    @SerializedName("wins")
    val wins: Int,
    @SerializedName("losses")
    val losses: Int,
    @SerializedName("position")
    val position: String
) {
    fun toEntity() = SummonerPosition(games, wins, losses, position)
}