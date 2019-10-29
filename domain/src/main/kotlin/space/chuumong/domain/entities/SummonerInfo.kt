package space.chuumong.domain.entities

data class SummonerInfo(

    val name: String,
    val level: Int,
    val profileImageUrl: String,
    val leagues: List<SummonerLeague>
)

data class SummonerLeague(
    val win: Int,
    val loss: Int,
    val tireRank: SummonerTireRank
)

data class SummonerTireRank(
    val name: String,
    val tire: String,
    val tireImageUrl: String,
    val lp: Int
)