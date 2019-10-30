package space.chuumong.data.remote.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import space.chuumong.data.remote.model.SummonerInfoResponse
import space.chuumong.data.remote.model.SummonerMatchGameResponse

interface ApiService {

    @GET("summoner/{name}")
    fun getSummonerProfile(@Path("name") name: String): Single<SummonerInfoResponse>

    @GET("summoner/{name}/matches")
    fun getSummonerMatchGame(@Path("name") name: String, @Query("lastMatch") lastMatch: Int? = null): Single<SummonerMatchGameResponse>
}