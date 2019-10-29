package space.chuumong.lolstats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import space.chuumong.data.Result
import space.chuumong.domain.entities.SummonerInfo
import space.chuumong.domain.usecases.GetSummonerInfo

class SummonerViewModel(private val getSummonerInfo: GetSummonerInfo) : BaseViewModel() {

    private val _summonerName = MutableLiveData<String>()
    private val _summonerLevel = MutableLiveData<Int>()
    private val _summonerProfileImage = MutableLiveData<String>()

    val summonerName: LiveData<String>
        get() = _summonerName
    val summonerLevel: LiveData<Int>
        get() = _summonerLevel
    val summonerProfileImage: LiveData<String>
        get() = _summonerProfileImage

    fun getSummonerInfo(name: String, result: Result<SummonerInfo>) {
        add(getSummonerInfo.get(name).subscribe({
            _summonerName.value = it.name
            _summonerLevel.value = it.level
            _summonerProfileImage.value = it.profileImageUrl
            
            result.onSuccess(it)
        }, {
            result.onFail(it)
        }))
    }
}