package space.chuumong.lolstats.di

import org.koin.dsl.module
import space.chuumong.lolstats.viewmodel.SummonerGameViewModel
import space.chuumong.lolstats.viewmodel.SummonerProfileViewModel

val viewModelModule = module {
    factory { SummonerProfileViewModel(get()) }

    factory { SummonerGameViewModel(get(), get()) }
}