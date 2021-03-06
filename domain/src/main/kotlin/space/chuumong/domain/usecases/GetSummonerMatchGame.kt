package space.chuumong.domain.usecases

import io.reactivex.Single
import space.chuumong.domain.entities.SummonerMatchGame
import space.chuumong.domain.repositories.SummonerRepository

class GetSummonerMatchGame(private val repository: SummonerRepository) :
    UseCase<Map<String, String>, SummonerMatchGame>() {

    companion object {
        private val NAME = "params:name"
    }

    override fun run(params: Map<String, String>): Single<SummonerMatchGame> {
        val name = params[NAME] ?: throw NullPointerException()

        return repository.getSummonerMatchGame(name)
    }

    fun get(name: String): Single<SummonerMatchGame> {
        val params = hashMapOf<String, String>()
        params[NAME] = name

        return execute(params)
    }
}