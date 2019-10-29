package space.chuumong.data.remote.datasource

import io.reactivex.Single
import space.chuumong.data.remote.api.ApiService
import space.chuumong.data.remote.model.SummonerInfoResponse
import space.chuumong.data.remote.model.SummonerResponse

class SummonerRemoteDataSource(private val apiService: ApiService) {

    fun getSummonerInfo(name: String): Single<SummonerResponse> {
        return apiService.getSummonerInfo(name)
    }

}