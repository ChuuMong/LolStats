package space.chuumong.data.remote.model

import com.google.gson.annotations.SerializedName
import space.chuumong.domain.entities.SummonerInfo
import space.chuumong.domain.entities.SummonerLeague
import space.chuumong.domain.entities.SummonerTireRank

data class SummonerResponse(
    @SerializedName("summoner")
    val summonerInfo: SummonerInfoResponse
) {
    fun toEntity() = summonerInfo.toEntity()
}

data class SummonerInfoResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("level")
    val level: Int,
    @SerializedName("profileImageUrl")
    val profileImageUrl: String,
    @SerializedName("leagues")
    val leagues: List<SummonerLeagueResponse>
) {
    fun toEntity(): SummonerInfo {
        val leagues = mutableListOf<SummonerLeague>()
        this.leagues.forEach { leagues.add(it.toEntity()) }

        return SummonerInfo(name, level, profileImageUrl, leagues)
    }
}

data class SummonerLeagueResponse(
    @SerializedName("wins")
    val win: Int,
    @SerializedName("losses")
    val loss: Int,
    @SerializedName("tierRank")
    val tireRank: SummonerTireRankResponse
) {
    fun toEntity() = SummonerLeague(win, loss, tireRank.toEntity())
}

data class SummonerTireRankResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("tier")
    val tire: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("lp")
    val lp: Int
) {
    fun toEntity() = SummonerTireRank(name, tire, imageUrl, lp)
}