package space.chuumong.data.repositories

import io.reactivex.Single
import space.chuumong.data.mapper.SummonerMapper
import space.chuumong.data.remote.datasource.SummonerRemoteDataSource
import space.chuumong.domain.repositories.SummonerRepository
import space.chuumong.domain.usecases.UseCase

class SummonerRepositoryImpl(
    private val remoteDataSource: SummonerRemoteDataSource,
    private val mapper: SummonerMapper
) : SummonerRepository {

    override fun getSummonerInfo(name: String): Single<UseCase.None> {
        return remoteDataSource.getSummonerInfo(name)
    }
}