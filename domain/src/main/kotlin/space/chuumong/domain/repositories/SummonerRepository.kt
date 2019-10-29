package space.chuumong.domain.repositories

import io.reactivex.Single
import space.chuumong.domain.entities.SummonerInfo

interface SummonerRepository {

    fun getSummonerInfo(name: String): Single<SummonerInfo>
}