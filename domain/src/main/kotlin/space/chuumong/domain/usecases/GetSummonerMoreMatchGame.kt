package space.chuumong.domain.usecases

import io.reactivex.Single
import space.chuumong.domain.entities.SummonerGame
import space.chuumong.domain.repositories.SummonerRepository


/**
 * Created by Home on 2019-10-31.
 */
class GetSummonerMoreMatchGame(private val repository: SummonerRepository) :
    UseCase<Map<String, Any>, List<SummonerGame>>() {
    companion object {
        private const val NAME = "params:name"
        private const val DATE = "params:date"
    }

    override fun run(params: Map<String, Any>): Single<List<SummonerGame>> {
        val name = params[NAME] as? String ?: throw NullPointerException()
        val date = params[DATE] as? Int ?: throw NullPointerException()

        return repository.getSummonerMoreMatchGame(name, date)
    }

    fun get(name: String, date: Int): Single<List<SummonerGame>> {
        val params = hashMapOf<String, Any>()
        params[NAME] = name
        params[DATE] = date

        return execute(params)
    }
}