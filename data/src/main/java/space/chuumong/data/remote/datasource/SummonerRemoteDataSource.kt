package space.chuumong.data.remote.datasource

import io.reactivex.Single
import space.chuumong.data.remote.api.ApiService
import space.chuumong.data.remote.model.SummonerInfoResponse
import space.chuumong.data.remote.model.SummonerMatchGameResponse
import space.chuumong.domain.entities.SummonerGame

class SummonerRemoteDataSource(private val apiService: ApiService) {

    fun getSummonerProfile(name: String): Single<SummonerInfoResponse> {
        return apiService.getSummonerProfile(name)
    }

    fun getSummonerMatchGame(name: String, lastMatch: Int? = null): Single<SummonerMatchGameResponse> {
        return apiService.getSummonerMatchGame(name, lastMatch)
    }
}