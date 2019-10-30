package space.chuumong.data.repositories

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import space.chuumong.data.mapper.SummonerMapper
import space.chuumong.data.remote.datasource.SummonerRemoteDataSource
import space.chuumong.domain.entities.Summoner
import space.chuumong.domain.entities.SummonerMatchGame
import space.chuumong.domain.entities.SummonerProfile
import space.chuumong.domain.repositories.SummonerRepository

class SummonerRepositoryImpl(
    private val remoteDataSource: SummonerRemoteDataSource,
    private val mapper: SummonerMapper
) : SummonerRepository {

    override fun getSummonerInfo(name: String): Single<Summoner> {
        return getSummonerProfile(name)
            .zipWith(getSummonerMatchGame(name),
                BiFunction<SummonerProfile, SummonerMatchGame, Summoner> { profile, matchGame ->
                    Summoner(profile, matchGame)
                })
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getSummonerProfile(name: String): Single<SummonerProfile> {
        return remoteDataSource.getSummonerProfile(name)
            .map(mapper.toSummonerProfileEntity())
    }

    private fun getSummonerMatchGame(name: String): Single<SummonerMatchGame> {
        return remoteDataSource.getSummonerMatchGame(name)
            .map(mapper.toSummonerMatchGameEntity())

    }
}