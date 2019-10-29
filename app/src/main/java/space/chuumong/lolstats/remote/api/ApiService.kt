package space.chuumong.lolstats.remote.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("summoner/{name}")
    fun getSummonerInfo(@Path("name") name: String): Single<Any>
}