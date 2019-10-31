package space.chuumong.lolstats.di

import org.koin.dsl.module
import space.chuumong.lolstats.viewmodel.SummonerViewModel

val viewModelModule = module {
    factory { SummonerViewModel(get(), get()) }
}