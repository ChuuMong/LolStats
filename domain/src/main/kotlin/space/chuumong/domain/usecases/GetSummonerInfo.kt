package space.chuumong.domain.usecases

import io.reactivex.Single
import space.chuumong.domain.entities.Summoner
import space.chuumong.domain.entities.SummonerProfile
import space.chuumong.domain.repositories.SummonerRepository

class GetSummonerInfo(private val repository: SummonerRepository) :
    UseCase<Map<String, String>, Summoner>() {

    companion object {
        private val NAME = "params:name"
    }

    override fun run(params: Map<String, String>): Single<Summoner> {
        val name = params[NAME] ?: throw NullPointerException()

        return repository.getSummonerInfo(name)
    }

    fun get(name: String): Single<Summoner> {
        val params = hashMapOf<String, String>()
        params[NAME] = name

        return execute(params)
    }

}