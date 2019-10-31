package space.chuumong.lolstats.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import space.chuumong.data.Result
import space.chuumong.domain.entities.SummonerProfile
import space.chuumong.domain.usecases.GetSummonerProfile
import space.chuumong.lolstats.utils.SingleLiveEvent

class SummonerProfileViewModel(private val getSummonerProfile: GetSummonerProfile) :
    BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()

    private val _summonerName = MutableLiveData<String>()
    private val _summonerLevel = MutableLiveData<String>()
    private val _summonerProfileImage = MutableLiveData<String>()

    private val _onClickSummonerRefresh = SingleLiveEvent<Any>()

    val isLoading: LiveData<Boolean> get() = _isLoading

    val summonerName: LiveData<String> get() = _summonerName
    val summonerLevel: LiveData<String> get() = _summonerLevel
    val summonerProfileImage: LiveData<String> get() = _summonerProfileImage

    val onClickSummonerRefresh: LiveData<Any> get() = _onClickSummonerRefresh

    fun onClickSummonerRefresh() {
        _onClickSummonerRefresh.call()
    }

    @SuppressLint("DefaultLocale")
    fun getProfile(name: String, result: Result<SummonerProfile>) {
        _isLoading.value = true

        add(getSummonerProfile.get(name).subscribe({
            _summonerName.value = it.name.capitalize()
            _summonerLevel.value = it.level.toString()
            _summonerProfileImage.value = it.profileImageUrl

            _isLoading.value = false
            result.onSuccess(it)
        }, {
            _isLoading.value = false
            result.onFail(it)
        }))
    }

}