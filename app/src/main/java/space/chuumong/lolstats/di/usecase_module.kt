package space.chuumong.lolstats.di

import org.koin.dsl.module
import space.chuumong.domain.usecases.GetSummonerInfo

val useCaseModule = module {
    factory { GetSummonerInfo(get()) }
}