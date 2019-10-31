package space.chuumong.domain.repositories

import io.reactivex.Single
import space.chuumong.domain.entities.Summoner
import space.chuumong.domain.entities.SummonerGame
import space.chuumong.domain.entities.SummonerProfile

interface SummonerRepository {

    fun getSummonerInfo(name: String): Single<Summoner>

    fun getSummonerMoreMatchGame(name: String, date: Int): Single<List<SummonerGame>>
}