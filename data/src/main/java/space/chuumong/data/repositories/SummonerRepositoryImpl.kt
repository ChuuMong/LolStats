package space.chuumong.data.repositories

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import space.chuumong.data.mapper.SummonerMapper
import space.chuumong.data.remote.datasource.SummonerRemoteDataSource
import space.chuumong.domain.entities.SummonerInfo
import space.chuumong.domain.repositories.SummonerRepository

class SummonerRepositoryImpl(
    private val remoteDataSource: SummonerRemoteDataSource,
    private val mapper: SummonerMapper
) : SummonerRepository {

    override fun getSummonerInfo(name: String): Single<SummonerInfo> {
        return remoteDataSource.getSummonerInfo(name)
            .map(mapper.toSummonerInfoEntity())
            .observeOn(AndroidSchedulers.mainThread())
    }
}