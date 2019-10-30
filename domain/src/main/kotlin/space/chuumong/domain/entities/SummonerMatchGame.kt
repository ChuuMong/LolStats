package space.chuumong.domain.entities


data class SummonerMatchGame(
    val games: List<SummonerGame>,
    val resentWinCount: Int,
    val resentLossCount: Int,
    val totalKills: Int,
    val totalDeaths: Int,
    val totalAssists: Int,
    val mostChampions: List<SummonerMostChampion>,
    val positions: List<SummonerPosition>
)

data class SummonerGame(
    val champion: SummonerGameChampion,
    val spells: List<ImageUrl>,
    val items: List<ImageUrl>,
    val createDate: Int,
    val gameLength: Int,
    val gameType: String,
    val stats: SummonerGameStats,
    val peak: List<String>,
    val isWin: Boolean
)

data class SummonerGameChampion(
    val imageUrl: String,
    val level: Int
)

data class SummonerGameStats(
    val general: SummonerGameStatsGeneral
)

data class SummonerGameStatsGeneral(
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val kda: String,
    val killRate: String,
    val multiKill: String,
    val opScoreBadge: String
)

data class SummonerMostChampion(
    val name: String,
    val imageUrl: String,
    val game: Int,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val wins: Int,
    val losses: Int
)

enum class SummonerPositionType {
    TOP, JNG, MID, ADC, SUP
}

data class SummonerPosition(
    val games: Int,
    val wins: Int,
    val losses: Int,
    val position: String
)