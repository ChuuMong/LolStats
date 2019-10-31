package space.chuumong.domain.repositories

import io.reactivex.Single
import space.chuumong.domain.entities.SummonerGame
import space.chuumong.domain.entities.SummonerMatchGame
import space.chuumong.domain.entities.SummonerProfile

interface SummonerRepository {
    fun getSummonerProfile(name: String): Single<SummonerProfile>

    fun getSummonerMatchGame(name: String): Single<SummonerMatchGame>

    fun getSummonerMoreMatchGame(name: String, date: Int): Single<List<SummonerGame>>
}