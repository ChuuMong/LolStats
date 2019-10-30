package space.chuumong.lolstats.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import space.chuumong.data.Result
import space.chuumong.data.utils.MathUtils
import space.chuumong.domain.entities.Summoner
import space.chuumong.domain.entities.SummonerProfile
import space.chuumong.domain.usecases.GetSummonerInfo
import space.chuumong.lolstats.utils.SingleLiveEvent

class SummonerViewModel(private val getSummonerInfo: GetSummonerInfo) : BaseViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()

    private val _summonerName = MutableLiveData<String>()
    private val _summonerLevel = MutableLiveData<String>()
    private val _summonerProfileImage = MutableLiveData<String>()
    private val _resentGameCount = MutableLiveData<Int>()
    private val _resentWinCount = MutableLiveData<Int>()
    private val _resentLossCount = MutableLiveData<Int>()
    private val _resentKillAverage = MutableLiveData<Float>()
    private val _resentDeathAverage = MutableLiveData<Float>()
    private val _resentAssistAverage = MutableLiveData<Float>()
    private val _resentKda = MutableLiveData<Float>()
    private val _resentWinRateText = MutableLiveData<Int>()
    private val _summonerPosition = MutableLiveData<String>()
    private val _summonerPositionWinRate = MutableLiveData<Int>()

    private val _mostFirstChampionImage = MutableLiveData<String>()
    private val _mostFirstChampionWinRate = MutableLiveData<Int>()
    private val _mostSecondChampionImage = MutableLiveData<String>()
    private val _mostSecondChampionWinRate = MutableLiveData<Int>()
    private val _isMostFirstChampion = MutableLiveData<Boolean>()
    private val _isMostSecondChampion = MutableLiveData<Boolean>()

    private val _onClickSummonerRefresh = SingleLiveEvent<Any>()

    val isLoading: LiveData<Boolean> get() = _isLoading

    val summonerName: LiveData<String> get() = _summonerName
    val summonerLevel: LiveData<String> get() = _summonerLevel
    val summonerProfileImage: LiveData<String> get() = _summonerProfileImage

    val resentGameCount: LiveData<Int> get() = _resentGameCount
    val resentWinCount: LiveData<Int> get() = _resentWinCount
    val resentLossCount: LiveData<Int> get() = _resentLossCount
    val resentKillAverage: LiveData<Float> get() = _resentKillAverage
    val resentDeathAverage: LiveData<Float> get() = _resentDeathAverage
    val resentAssistAverage: LiveData<Float> get() = _resentAssistAverage
    val resentKda: LiveData<Float> get() = _resentKda
    val resentWinRateText: LiveData<Int> get() = _resentWinRateText

    val mostFirstChampionImage: LiveData<String> get() = _mostFirstChampionImage
    val mostFirstChampionWinRate: LiveData<Int> get() = _mostFirstChampionWinRate
    val mostSecondChampionImage: LiveData<String> get() = _mostSecondChampionImage
    val mostSecondChampionWinRate: LiveData<Int> get() = _mostSecondChampionWinRate
    val isMostFirstChampion: LiveData<Boolean> get() = _isMostFirstChampion
    val isMostSecondChampion: LiveData<Boolean> get() = _isMostSecondChampion

    val summonerPosition: LiveData<String> get() = _summonerPosition
    val summonerPositionWinRate: LiveData<Int> get() = _summonerPositionWinRate

    val onClickSummonerRefresh: LiveData<Any>
        get() = _onClickSummonerRefresh

    fun onClickSummonerRefresh() {
        _onClickSummonerRefresh.call()
    }

    @SuppressLint("DefaultLocale")
    fun getSummonerInfo(name: String, result: Result<Summoner>) {
        _isLoading.value = true

        add(getSummonerInfo.get(name).subscribe({
            _summonerName.value = it.profile.name.capitalize()
            _summonerLevel.value = it.profile.level.toString()
            _summonerProfileImage.value = it.profile.profileImageUrl

            val resentGameCount = it.matchGame.games.size
            _resentGameCount.value = resentGameCount

            val winCount = it.matchGame.resentWinCount
            val lossCount = it.matchGame.resentLossCount
            _resentWinCount.value = winCount
            _resentLossCount.value = lossCount

            val totalKills = it.matchGame.totalDeaths.toFloat()
            val totalDeaths = it.matchGame.totalDeaths.toFloat()
            val totalAssists = it.matchGame.totalAssists.toFloat()

            _resentKillAverage.value = totalKills / resentGameCount
            _resentDeathAverage.value = totalDeaths / resentGameCount
            _resentAssistAverage.value = totalAssists / resentGameCount

            _resentKda.value = (totalKills + totalDeaths) / totalAssists

            _resentWinRateText.value = MathUtils.getWinRate(winCount, lossCount)

            val summonerPosition = it.matchGame.positions[0]
            _summonerPosition.value = summonerPosition.position
            _summonerPositionWinRate.value =
                MathUtils.getWinRate(summonerPosition.wins, summonerPosition.losses)

            if (it.matchGame.mostChampions.size < 2) {
                _isLoading.value = false
                result.onFail(ArrayIndexOutOfBoundsException())
                return@subscribe
            }

            val firstMostChampion = it.matchGame.mostChampions[0]
            val firstMostChampionWinRate =
                MathUtils.getWinRate(firstMostChampion.wins, firstMostChampion.losses)
            val secondMostChampion = it.matchGame.mostChampions[1]
            val secondMostChampionWinRate =
                MathUtils.getWinRate(secondMostChampion.wins, secondMostChampion.losses)

            _mostFirstChampionImage.value = firstMostChampion.imageUrl
            _mostFirstChampionWinRate.value = firstMostChampionWinRate
            _mostSecondChampionImage.value = secondMostChampion.imageUrl
            _mostSecondChampionWinRate.value = secondMostChampionWinRate

            when {
                firstMostChampionWinRate < secondMostChampionWinRate -> {
                    _isMostSecondChampion.value = true
                    _isMostFirstChampion.value = false
                }
                secondMostChampionWinRate < firstMostChampionWinRate -> {
                    _isMostSecondChampion.value = false
                    _isMostFirstChampion.value = true
                }
                else -> {
                    _isMostSecondChampion.value = true
                    _isMostFirstChampion.value = true
                }
            }

            _isLoading.value = false
            result.onSuccess(it)
        }, {
            _isLoading.value = false
            result.onFail(it)
        }))
    }
}