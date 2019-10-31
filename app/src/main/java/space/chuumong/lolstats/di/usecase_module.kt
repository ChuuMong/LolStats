package space.chuumong.lolstats.di

import org.koin.dsl.module
import space.chuumong.domain.usecases.GetSummonerMatchGame
import space.chuumong.domain.usecases.GetSummonerProfile
import space.chuumong.domain.usecases.GetSummonerMoreMatchGame

val useCaseModule = module {
    factory { GetSummonerProfile(get()) }

    factory { GetSummonerMatchGame(get()) }
    
    factory { GetSummonerMoreMatchGame(get()) }
}