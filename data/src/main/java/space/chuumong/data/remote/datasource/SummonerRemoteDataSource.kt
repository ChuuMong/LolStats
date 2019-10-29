package space.chuumong.data.remote.datasource

import io.reactivex.Single
import space.chuumong.data.remote.api.ApiService
import space.chuumong.domain.usecases.UseCase


/**
 * Created by Home on 2019-10-29.
 */
class SummonerRemoteDataSource(private val apiService: ApiService) {

    fun getSummonerInfo(name: String): Single<UseCase.None> {
        return apiService.getSummonerInfo(name)
    }

}