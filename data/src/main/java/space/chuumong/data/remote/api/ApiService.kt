package space.chuumong.data.remote.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import space.chuumong.data.remote.model.SummonerInfoResponse
import space.chuumong.data.remote.model.SummonerResponse
import space.chuumong.domain.usecases.UseCase

interface ApiService {

    @GET("summoner/{name}")
    fun getSummonerInfo(@Path("name") name: String): Single<SummonerResponse>
}