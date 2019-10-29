package space.chuumong.lolstats.di

import org.koin.dsl.module
import space.chuumong.data.mapper.SummonerMapper
import space.chuumong.data.remote.datasource.SummonerRemoteDataSource
import space.chuumong.data.repositories.SummonerRepositoryImpl
import space.chuumong.domain.repositories.SummonerRepository

val summonerDataModule = module {
    factory { SummonerMapper() }
    factory { SummonerRemoteDataSource(get()) }
    factory { SummonerRepositoryImpl(get(), get()) as SummonerRepository }
}