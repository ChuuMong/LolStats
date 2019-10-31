package space.chuumong.data.repositories

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import space.chuumong.data.mapper.SummonerMapper
import space.chuumong.data.remote.datasource.SummonerRemoteDataSource
import space.chuumong.domain.entities.SummonerGame
import space.chuumong.domain.entities.SummonerMatchGame
import space.chuumong.domain.entities.SummonerProfile
import space.chuumong.domain.repositories.SummonerRepository

class SummonerRepositoryImpl(
    private val remoteDataSource: SummonerRemoteDataSource,
    private val mapper: SummonerMapper
) : SummonerRepository {

    override fun getSummonerProfile(name: String): Single<SummonerProfile> {
        return remoteDataSource.getSummonerProfile(name)
            .map(mapper.toSummonerProfileEntity())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSummonerMatchGame(name: String): Single<SummonerMatchGame> {
        return remoteDataSource.getSummonerMatchGame(name)
            .map(mapper.toSummonerMatchGameEntity())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun getSummonerMoreMatchGame(name: String, date: Int): Single<List<SummonerGame>> {
        return remoteDataSource.getSummonerMatchGame(name, date)
            .map(mapper.toSummonerMatchGameEntity())
            .map {
                it.games
            }
            .observeOn(AndroidSchedulers.mainThread())
    }
}