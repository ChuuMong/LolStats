package space.chuumong.domain.repositories

import io.reactivex.Single
import space.chuumong.domain.entities.Summoner
import space.chuumong.domain.entities.SummonerProfile

interface SummonerRepository {

    fun getSummonerInfo(name: String): Single<Summoner>
}