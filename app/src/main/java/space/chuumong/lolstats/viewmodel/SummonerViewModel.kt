package space.chuumong.lolstats.viewmodel

import space.chuumong.data.Result
import space.chuumong.domain.usecases.GetSummonerInfo
import space.chuumong.domain.usecases.UseCase

class SummonerViewModel(private val getSummonerInfo: GetSummonerInfo) : BaseViewModel() {

    fun getSummonerInfo(name: String, result: Result<UseCase.None>) {
        add(getSummonerInfo.get(name).subscribe({
            result.onSuccess(it)
        }, {
            result.onFail(it)
        }))
    }
}