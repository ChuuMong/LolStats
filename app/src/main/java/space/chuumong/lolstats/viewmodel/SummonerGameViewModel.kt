package space.chuumong.lolstats.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import space.chuumong.data.Result
import space.chuumong.data.utils.MathUtils
import space.chuumong.domain.entities.SummonerGame
import space.chuumong.domain.entities.SummonerMatchGame
import space.chuumong.domain.usecases.GetSummonerMatchGame
import space.chuumong.domain.usecases.GetSummonerMoreMatchGame

class SummonerGameViewModel(
    private val getSummonerMatchGame: GetSummonerMatchGame,
    private val getSummonerMoreMatchGame: GetSummonerMoreMatchGame
) : BaseViewModel() {

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

    fun getMatchGame(name: String, result: Result<SummonerMatchGame>) {
        add(getSummonerMatchGame.get(name).subscribe({
            val resentGameCount = it.games.size
            _resentGameCount.value = resentGameCount

            val winCount = it.resentWinCount
            val lossCount = it.resentLossCount
            _resentWinCount.value = winCount
            _resentLossCount.value = lossCount

            val totalKills = it.totalDeaths.toFloat()
            val totalDeaths = it.totalDeaths.toFloat()
            val totalAssists = it.totalAssists.toFloat()

            _resentKillAverage.value = totalKills / resentGameCount
            _resentDeathAverage.value = totalDeaths / resentGameCount
            _resentAssistAverage.value = totalAssists / resentGameCount

            _resentKda.value = (totalKills + totalDeaths) / totalAssists

            _resentWinRateText.value = MathUtils.getWinRate(winCount, lossCount)

            val summonerPosition = it.positions[0]
            _summonerPosition.value = summonerPosition.position
            _summonerPositionWinRate.value =
                MathUtils.getWinRate(summonerPosition.wins, summonerPosition.losses)

            val firstMostChampion = it.mostChampions[0]
            val firstMostChampionWinRate =
                MathUtils.getWinRate(firstMostChampion.wins, firstMostChampion.losses)

            _mostFirstChampionImage.value = firstMostChampion.imageUrl
            _mostFirstChampionWinRate.value = firstMostChampionWinRate

            val secondMostChampion = try {
                it.mostChampions[1]
            } catch (e: ArrayIndexOutOfBoundsException) {
                null
            }

            val secondMostChampionWinRate = if (secondMostChampion != null) {
                MathUtils.getWinRate(secondMostChampion.wins, secondMostChampion.losses)
            } else {
                null
            }

            _mostSecondChampionImage.value = secondMostChampion?.imageUrl
            _mostSecondChampionWinRate.value = secondMostChampionWinRate

            if (secondMostChampionWinRate != null) {
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
            } else {
                _isMostFirstChampion.value = true
            }

            result.onSuccess(it)
        }, {
            result.onFail(it)
        }))
    }

    fun getMoreMatchGame(name: String, date: Int, result: Result<List<SummonerGame>>) {
        add(getSummonerMoreMatchGame.get(name, date).subscribe({
            result.onSuccess(it)
        }, {
            result.onFail(it)
        }))
    }
}