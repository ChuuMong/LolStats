package space.chuumong.lolstats.di

import org.koin.dsl.module
import space.chuumong.domain.usecases.GetSummonerInfo
import space.chuumong.domain.usecases.GetSummonerMoreMatchGame

val useCaseModule = module {
    factory { GetSummonerInfo(get()) }

    factory { GetSummonerMoreMatchGame(get()) }
}